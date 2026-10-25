#!/usr/bin/env python3
"""Fetch English text at the exact Quran verse or aligned Arabic hadith record.

The Arabic source remains authoritative. These compact companion files are checked in so
the TV does not rely on another live API. Missing or mismatched records are omitted.
"""
import json
import hashlib
import urllib.request
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
SHARED = ROOT / "assets/shared"
QURAN_URL = "https://api.alquran.cloud/v1/quran/en.sahih"
HADITH_URL = "https://cdn.jsdelivr.net/gh/fawazahmed0/hadith-api@1/editions/eng-{}.min.json"
HADITH_EDITION = {
    "صحيح البخاري": "bukhari", "صحيح مسلم": "muslim", "سنن أبي داود": "abudawud",
    "جامع الترمذي": "tirmidhi", "سنن النسائي": "nasai", "سنن ابن ماجه": "ibnmajah",
}


def fetch(url):
    req = urllib.request.Request(url, headers={"User-Agent": "UrTV/English-data"})
    with urllib.request.urlopen(req, timeout=120) as response:
        return json.load(response)


def main():
    verses = json.loads((SHARED / "verses.json").read_text(encoding="utf-8"))
    quran = fetch(QURAN_URL)["data"]
    edition = quran["edition"]
    if edition["identifier"] != "en.sahih" or edition["englishName"] != "Saheeh International":
        raise ValueError("unexpected Quran translation edition")
    by_ref = {}
    for surah in quran["surahs"]:
        for ayah in surah["ayahs"]:
            by_ref[f'{surah["number"]}:{ayah["numberInSurah"]}'] = {
                "text": ayah["text"].strip(), "surah": surah["englishName"],
                "translator": edition["englishName"], "edition": edition["identifier"],
            }
    out_verses = {v["ref"]: by_ref[v["ref"]] for v in verses if v["ref"] in by_ref}
    if len(out_verses) != len({v["ref"] for v in verses}):
        raise ValueError("not every Arabic verse has an exact English reference")
    (SHARED / "verses-en.json").write_text(json.dumps(out_verses, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")

    hadith = json.loads((SHARED / "hadith.json").read_text(encoding="utf-8"))
    needed = {(h["full"]["book"], h["full"]["number"]): h["full"] for h in hadith if h.get("full")}
    out_hadith = {}
    for book, edition_name in HADITH_EDITION.items():
        # The app cites the printed Arabic number. In Muslim it differs from the
        # API's sequential hadithnumber (e.g. printed 768 is record 1807).
        # Match the source Arabic narration to its exact API row first, then
        # use that row's sequential ID to find the aligned English record.
        arabic_rows = fetch(HADITH_URL.format(edition_name).replace("eng-", "ara-"))["hadiths"]
        english_rows = fetch(HADITH_URL.format(edition_name))["hadiths"]
        by_arabic_text = {}
        for row in arabic_rows:
            by_arabic_text.setdefault(row.get("text"), []).append(row)
        indexed = {h["hadithnumber"]: h for h in english_rows}
        for (wanted_book, number), arabic in needed.items():
            if wanted_book != book:
                continue
            matches = [row for row in by_arabic_text.get(arabic["text"], [])
                if int(float(row.get("arabicnumber") or row["hadithnumber"])) == number]
            if len(matches) != 1:
                continue
            source = matches[0]
            row = indexed.get(source["hadithnumber"])
            if not row or row.get("arabicnumber") != source.get("arabicnumber") \
                    or row.get("reference") != source.get("reference"):
                continue
            text = (row.get("text") or "").strip()
            if not text or any("\u0600" <= ch <= "\u06ff" for ch in text):
                continue
            out_hadith[f"{book}|{number}"] = {"text": text, "book": book,
                "number": number, "edition": "eng-" + edition_name,
                "hadithnumber": source["hadithnumber"], "reference": row.get("reference"),
                "arabic_sha256": hashlib.sha256(arabic["text"].encode("utf-8")).hexdigest()}
        print(book, sum(k.startswith(book + "|") for k in out_hadith))
    (SHARED / "hadith-en.json").write_text(json.dumps(out_hadith, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
    print("verses", len(out_verses), "hadith", len(out_hadith), "of", len(needed))


if __name__ == "__main__":
    main()

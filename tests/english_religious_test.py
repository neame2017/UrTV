#!/usr/bin/env python3
"""English religious text may be shown only at a matching Arabic reference."""
import importlib.util
import hashlib
import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
SHARED = ROOT / "assets/shared"
spec = importlib.util.spec_from_file_location("urtv_build", ROOT / "src/build.py")
build = importlib.util.module_from_spec(spec)
spec.loader.exec_module(build)

arabic_verses = json.loads((SHARED / "verses.json").read_text(encoding="utf-8"))
english_verses = json.loads((SHARED / "verses-en.json").read_text(encoding="utf-8"))
assert len(english_verses) == len({v["ref"] for v in arabic_verses})
assert all(english_verses[v["ref"]]["edition"] == "en.sahih"
           and english_verses[v["ref"]]["translator"] == "Saheeh International"
           and english_verses[v["ref"]]["text"] for v in arabic_verses)
assert all("english" in v and "surah_en" in v for v in build.verses_for_screen())

arabic_hadith = json.loads((SHARED / "hadith.json").read_text(encoding="utf-8"))
english_hadith = json.loads((SHARED / "hadith-en.json").read_text(encoding="utf-8"))
source = {(h["full"]["book"], h["full"]["number"]): h["full"]["text"]
          for h in arabic_hadith if h.get("full")}
assert all((h["book"], h["number"]) in source and h["text"]
           and h.get("hadithnumber")
           and h.get("arabic_sha256") == hashlib.sha256(source[(h["book"], h["number"])].encode("utf-8")).hexdigest()
           and h["edition"].startswith("eng-") for h in english_hadith.values())
muslim_768 = english_hadith["صحيح مسلم|768"]
assert muslim_768["hadithnumber"] == 1807
assert "gets up at night" in muslim_768["text"] and "two short rak'ahs" in muslim_768["text"]
shown = build.hadith_for_screen()
assert sum("english" in h for h in shown) >= 300
assert all(("english" in h) == ("source_en" in h) for h in shown)
print("PASS exact verse references and attributed English edition")
print("PASS English hadith matches the exact Arabic API record; missing translations remain Arabic only")

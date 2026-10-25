#!/usr/bin/env python3
"""English religious text may be shown only at a matching Arabic reference."""
import importlib.util
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
source = {(h["full"]["book"], h["full"]["number"]) for h in arabic_hadith if h.get("full")}
assert all((h["book"], h["number"]) in source and h["text"]
           and h["edition"].startswith("eng-") for h in english_hadith.values())
shown = build.hadith_for_screen()
assert sum("english" in h for h in shown) >= 300
assert all(("english" in h) == ("source_en" in h) for h in shown)
print("PASS exact verse references and attributed English edition")
print("PASS hadith English book and number matches Arabic source; missing translations remain Arabic only")

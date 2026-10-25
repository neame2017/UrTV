package sa.urtv.screensaver;

import android.icu.text.Transliterator;
import android.os.Build;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Pattern;

/** Settings interface words. The Arabic religious source text is never passed here. */
final class EnglishText {
    private EnglishText() {}
    private static final Pattern ARABIC = Pattern.compile("[\\p{IsArabic}]");
    private static final class Icu {
        static final Transliterator INSTANCE = Transliterator.getInstance("Arabic-Latin; Latin-ASCII");
    }

    private static final String[][] WORDS = {
        {"تلفزيونك", "UrTV"}, {"إعدادات شاشة التوقف", "Screen saver settings"},
        {"يمين/يسار وأعلى/أسفل بين الصفوف  •  اختيار لفتح الصف  •  رجوع للخروج", "Use arrows to move  •  Select to open  •  Back to exit"},
        {"معاينة الشاشة", "Preview screen"}, {"شاهد الشاشة الآن", "Show the screen now"},
        {"الموقع", "Location"}, {"المدينة", "City"}, {"جدة", "Jeddah"}, {"الرياض", "Riyadh"},
        {"منطقة الرياض", "Riyadh Region"}, {"منطقة مكة المكرمة", "Makkah Region"},
        {"منطقة المدينة المنورة", "Madinah Region"}, {"المنطقة الشرقية", "Eastern Province"},
        {"منطقة القصيم", "Qassim Region"}, {"منطقة عسير", "Asir Region"},
        {"منطقة تبوك", "Tabuk Region"}, {"منطقة حائل", "Ha'il Region"},
        {"منطقة الحدود الشمالية", "Northern Borders Region"}, {"منطقة جازان", "Jazan Region"},
        {"منطقة نجران", "Najran Region"}, {"منطقة الباحة", "Al-Bahah Region"},
        {"منطقة الجوف", "Al-Jouf Region"},
        {"مكة المكرمة", "Makkah"}, {"المدينة المنورة", "Madinah"},
        {"الدمام", "Dammam"}, {"الخبر", "Khobar"}, {"الطائف", "Taif"},
        {"أبها", "Abha"}, {"تبوك", "Tabuk"}, {"حائل", "Ha'il"},
        {"نجران", "Najran"}, {"جازان", "Jazan"}, {"بريدة", "Buraidah"},
        {"المباريات", "Matches"}, {"الفرق المتابعة", "Followed teams"}, {"الدوريات كاملة", "Full leagues"},
        {"رياضات أخرى", "Other sports"}, {"العرض", "Display"}, {"اللغة", "Language"},
        {"الأرقام", "Digits"}, {"تنبيه قبل الأذان", "Adhan reminder"},
        {"تعديل وقت الصلاة", "Adjust prayer times"}, {"شريط السنة تحت الساعة", "Year strip under clock"},
        {"المناسبات الوطنية", "National occasions"}, {"الخانة اليسرى", "Left slot"},
        {"العدّ التنازلي", "Countdown"}, {"الأسواق (أعلى اليسار)", "Markets (top left)"},
        {"عرض الأسواق", "Market display"}, {"الأسواق", "Markets"}, {"بجانب الساعة", "Beside the clock"},
        {"المحتوى", "Content"}, {"شكل البطاقة", "Card style"}, {"الشاشة الرئيسية", "Home screen"},
        {"البطاقة الأولى", "First card"}, {"البطاقة الثانية", "Second card"}, {"البطاقة الثالثة", "Third card"},
        {"التحية والطقس", "Greeting and weather"}, {"ترتيب التطبيقات المفضلة", "Arrange favorite apps"},
        {"قائمة ترتّب فيها المفضلة بالأسهم", "Arrange favorites with the arrow keys"},
        {"جديد المسلسلات", "New series"}, {"التصنيفات", "Genres"}, {"اللغات", "Languages"},
        {"مسلسلات مختارة", "Selected series"}, {"أقل تقييم", "Minimum rating"},
        {"الأسهم", "Stocks"}, {"قائمة الأسهم", "Stock watchlist"}, {"التوقيت", "Timing"},
        {"تبديل الصورة كل", "Change photo every"}, {"صوري", "My photos"},
        {"بدء شاشة التوقف بعد", "Start screen saver after"}, {"التطبيق", "App"},
        {"التحديث التلقائي للصفحة", "Automatic page update"}, {"تحديث الشاشة الآن", "Update screen now"},
        {"ينزّل أحدث نسخة من الشاشة", "Download the newest screen"},
        {"يعمل · شريط في أعلى الشاشة آخر ١٠ دقائق، ثم حتى الإقامة", "On · Top bar in the last 10 minutes before Adhan, until Iqama"},
        {"يعمل · لكل صلاة دقائقها، من −٢٠ إلى +٢٠ (الشروق لا يتغيّر)", "On · Adjust each prayer by −20 to +20 minutes (sunrise unchanged)"},
        {"متوقف · الأوقات كما تُحسب", "Off · Calculated prayer times"},
        {"يعمل · نقاط السنة تحت الساعة، واليوم يلمع", "On · Year dots under the clock, with today highlighted"},
        {"يعمل · عند بدء الشاشة، كل ٦ ساعات", "On · At screen start and every 6 hours"},
        {"يعمل", "On"}, {"تعمل", "On"}, {"متوقف", "Off"}, {"متوقفة", "Off"},
        {"بدون اختيار = بدون", "No selection = none"}, {"بدون اختيار = الكل", "No selection = all"},
        {"بدون", "None"}, {"لا شيء", "None"}, {"الكل", "All"}, {"العربية", "Arabic"},
        {"عربية 123", "Latin 123"}, {"هندية ١٢٣", "Arabic-Indic ١٢٣"},
        {"مواقيت الصلاة اليوم", "Today's prayer times"}, {"طقس الأيام القادمة", "Coming days' weather"},
        {"طقس الساعات القادمة", "Coming hours' weather"}, {"الصلوات في صف", "Prayers in a row"},
        {"مباريات فرقك القادمة", "Your teams' upcoming matches"},
        {"آية أو حديث اليوم", "Today's verse or hadith"}, {"حكمة اليوم", "Wisdom of the day"},
        {"أذكار الصباح والمساء", "Morning and evening remembrances"},
        {"المهام", "Tasks"}, {"السنة", "Year"}, {"البطاقة الذكية", "Smart card"}, {"يومك", "Your day"},
        {"الأسواق (حتى", "Markets (up to"}, {"حفظ", "Save"}, {"إلغاء", "Cancel"},
        {"إضافة فريق", "Add a team"}, {"إضافة مسلسل", "Add a series"}, {"إضافة تطبيق", "Add an app"},
        {"المنطقة", "Region"}, {"بحث", "Search"}, {"اختر المكان", "Choose place"},
        {"اختر الفريق", "Choose team"}, {"اختر المسلسل", "Choose series"},
        {"المنتخبات", "National teams"}, {"الدوري", "League"}, {"منتخب", "National team"},
        {"لا توجد نتائج", "No results"}, {"لا توجد فرق متابعة", "No followed teams"},
        {"لا توجد مسلسلات مختارة", "No selected series"}, {"مضاف مسبقاً", "Already added"},
        {"متابَع مسبقاً", "Already followed"}, {"اسم المدينة أو القرية", "City or village name"},
        {"اسم المسلسل", "Series title"}, {"حذف كل صوري؟", "Delete all my photos?"},
        {"حذف كل صوري", "Delete all my photos"}, {"حذف", "Delete"},
        {"تُضاف إلى صور التطبيق", "Add to app photos"}, {"صوري فقط", "Only my photos"},
        {"لا توجد صور", "No photos"}, {"لم يُحدَّد", "Not set"}, {"مسح", "Clear"},
        {"لا يوجد اتصال", "No connection"}, {"جارٍ التحقق…", "Checking…"},
        {"جارٍ البحث…", "Searching…"}, {"تعذرت قراءة النتائج", "Could not read results"},
        {"الفجر", "Fajr"}, {"الشروق", "Sunrise"}, {"الظهر", "Dhuhr"},
        {"العصر", "Asr"}, {"المغرب", "Maghrib"}, {"العشاء", "Isha"},
        {"تلقائي", "Automatic"}, {"إيقاف", "Off"}, {"الحالية", "Current"},
        {"داكنة", "Dark"}, {"زجاجية", "Glass"}, {"بإطار", "with frame"},
        {"الاتجاه فقط", "Direction only"}, {"السعر والنسبة", "Price and percentage"},
        {"الذهب (الجرام بالريال)", "Gold (SAR/gram)"}, {"الفضة (الجرام بالريال)", "Silver (SAR/gram)"},
        {"تاسي", "TASI"}, {"أرامكو", "Aramco"}, {"بيتكوين", "Bitcoin"}, {"ريبل", "XRP"},
        {"ذهب عيار ١٨ (ريال/غرام)", "Gold 18K (SAR/g)"},
        {"ذهب عيار ٢١ (ريال/غرام)", "Gold 21K (SAR/g)"},
        {"ذهب عيار ٢٢ (ريال/غرام)", "Gold 22K (SAR/g)"},
        {"ذهب عيار ٢٤ (ريال/غرام)", "Gold 24K (SAR/g)"},
        {"دقيقة واحدة", "One minute"}, {"دقيقتان", "Two minutes"},
        {"دقائق", "minutes"}, {"دقيقة", "minute"}, {"ثانية", "seconds"},
        {"يسار + · يمين − · موافق للتصفير", "Left + · Right − · Select to reset"},
        {"بحث عن مدينة أو قرية…", "Search for a city or village…"},
        {"بحث عن أي فريق أو منتخب…", "Search for any club or national team…"},
        {"تصفح الدوريات…", "Browse leagues…"},
        {"اسم الفريق بالعربية أو الإنجليزية (مثل الهلال أو \u2066Real Madrid\u2069)", "Team name in Arabic or English (for example Al-Hilal or Real Madrid)"},
        {"اطلب من مساعدك الذكي إضافة صورك (حتى ١٠)", "Ask your assistant to add up to 10 photos"},
        {"الحد الأعلى ٢٠ فريقاً", "Maximum 20 teams"},
        {"الفرق المتابعة (ألغِ التحديد للحذف)", "Followed teams (clear a selection to remove)"},
        {"المسلسلات المختارة (ألغِ التحديد للحذف)", "Selected series (clear a selection to remove)"},
        {"حتى ", "Up to "}, {" دوريات", " leagues"}, {" رموز", " symbols"}, {" أسواق", " markets"},
        {"أدخل من رمز إلى ", "Enter 1 to "}, {"اختر من سوق إلى ", "Choose 1 to "},
        {"تمت إضافة ", "Added "}, {"تمت متابعة ", "Now following "},
        {"ثبّت الواجهة الرئيسية «تلفزيونك» أولاً", "Set UrTV as the home screen first"},
        {"جارٍ جلب الاسم بالعربية…", "Looking up Arabic name…"},
        {"رمز غير صالح: ", "Invalid symbol: "},
        {"غيّرها من إعدادات التلفاز، أو بأمر من مساعدك", "Change this in TV settings or ask your assistant"},
        {"قائمة الأسهم (رمز تداول من ٤ أرقام أو رمز أمريكي، حتى ", "Stock watchlist (4-digit Tadawul or US symbol, up to "},
        {"لا توجد صور · يضيفها مساعدك الذكي", "No photos · ask your assistant to add them"},
        {"لا توجد نتائج، جرّب اسم الفريق بالإنجليزية", "No results. Try the team name in English"},
        {"تعذّر تحميل القائمة، جرّب البحث عن الفريق", "Could not load the list. Search for the team"},
        {"تعذّر تحميل هذا الدوري، جرّب البحث عن الفريق", "Could not load this league. Search for the team"},
        {"لم يُحدَّد تاريخ. يحدده المساعد على الكمبيوتر.", "No date set. The assistant can set one on the computer."},
        {"مثال: \u20662222 1120 AAPL NVDA\u2069", "Example: 2222 1120 AAPL NVDA"},
        {"يحدده التلفاز", "Set by the TV"}, {"حسنًا", "OK"},
        {"بدون حد", "No limit"},
        {"الأسواق (حتى ", "Markets (up to "}, {"الدوريات كاملة (حتى ", "Full leagues (up to "},
        {"، ", ", "}
    };

    static {
        Arrays.sort(WORDS, Comparator.comparingInt((String[] a) -> a[0].length()).reversed());
    }

    static String of(String source) {
        if (source == null) return null;
        String result = source;
        for (String[] row : WORDS) {
            String left = ARABIC.matcher(row[0].substring(0, 1)).find() ? "(?<![\\p{IsArabic}])" : "";
            String right = ARABIC.matcher(row[0].substring(row[0].length() - 1)).find() ? "(?![\\p{IsArabic}])" : "";
            String bounded = left + Pattern.quote(row[0]) + right;
            result = result.replaceAll(bounded, java.util.regex.Matcher.quoteReplacement(row[1]));
        }
        // Search results, team names and the built-in city catalogue can contain Arabic
        // names not present in the UI dictionary. Keep them readable in an English UI.
        if (ARABIC.matcher(result).find()) result = latinize(result);
        return result;
    }

    private static String latinize(String source) {
        if (Build.VERSION.SDK_INT >= 24) {
            return Icu.INSTANCE.transliterate(source);
        }
        // Android 5/6 lack android.icu. Keep the same language setting usable there.
        String letters = "ءآأإاٱبتثجحخدذرزسشصضطظعغفقكلمنهويىةؤئ";
        String[] latin = {"'", "a", "a", "i", "a", "a", "b", "t", "th", "j", "h", "kh",
            "d", "dh", "r", "z", "s", "sh", "s", "d", "t", "z", "'", "gh", "f", "q",
            "k", "l", "m", "n", "h", "w", "y", "a", "a", "w", "y"};
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            char ch = source.charAt(i);
            int index = letters.indexOf(ch);
            if (index >= 0) out.append(latin[index]);
            else if (ch < '\u064b' || ch > '\u065f') out.append(ch);
        }
        return out.toString();
    }
}

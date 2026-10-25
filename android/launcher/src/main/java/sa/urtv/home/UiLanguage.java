package sa.urtv.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.Transliterator;
import android.os.Build;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Language selected in the screensaver settings, shared through ConfigProvider. */
public final class UiLanguage {
    private static volatile boolean english;
    private static final Pattern ARABIC = Pattern.compile("[\\p{IsArabic}]");
    private static final class Icu {
        static final Transliterator INSTANCE = Transliterator.getInstance("Arabic-Latin; Latin-ASCII");
    }

    private UiLanguage() {}

    public static boolean english() { return english; }

    private static final String[][] WORDS = {
            {"صباح الخير", "Good morning"}, {"طاب يومك", "Have a good day"},
            {"مساء الخير", "Good evening"}, {"تصبح على خير", "Good night"},
            {"شاشة التوقف", "Screen saver"}, {"المصادر", "Inputs"},
            {"واي فاي", "Wi-Fi"}, {"المهام", "Tasks"}, {"الإعدادات", "Settings"},
            {"تلاوة وأذان", "Recitation and Adhan"}, {"مؤقت النوم", "Sleep timer"},
            {"كل التطبيقات", "All apps"}, {"البث التلفزيوني", "Live TV"},
            {"ترتيب المفضلة", "Arrange favorites"}, {"المفضلة", "Favorites"},
            {"إضافة تطبيق", "Add app"}, {"الترتيب الافتراضي", "Default order"},
            {"إزالة", "Remove"},
            {"اختيار على تطبيق لتحريكه بالأسهم (أعلى/أسفل) ثم اختيار لتثبيته.", "Select an app, move it with Up/Down, then Select to pin it."},
            {"تطبيقات.", "apps."}, {"رجعت المفضلة للترتيب الافتراضي", "Favorites restored to default order"},
            {"كل المصادر…", "All inputs…"}, {"غير متصل", "Disconnected"},
            {"شبكة سلكية", "Wired network"}, {"إلغاء", "Cancel"},
            {"لإطفاء الشاشة: فعّل «تلفزيونك: إطفاء الشاشة»", "To turn off the screen, enable UrTV screen off"},
            {"يُتلى الآن", "Now reciting"}, {"الظهر", "Dhuhr"}, {"العصر", "Asr"},
            {"المغرب", "Maghrib"}, {"العشاء", "Isha"}, {"الفجر", "Fajr"},
            {"الغروب", "Sunset"}, {"الشروق", "Sunrise"},
            {"سماء صافية غالبًا", "Mostly clear"}, {"سماء صافية", "Clear"},
            {"غائم جزئيًا", "Partly cloudy"}, {"سماء غائمة", "Cloudy"},
            {"أمطار غزيرة", "Heavy rain"}, {"أمطار", "Rain"},
            {"عواصف رعدية وبرد", "Thunderstorms with hail"}, {"عواصف رعدية", "Thunderstorms"},
            {"مطر متجمد", "Freezing rain"}, {"ثلوج", "Snow"}, {"رذاذ", "Drizzle"},
            {"ضباب", "Fog"}, {"غبار كثيف", "Heavy dust"}, {"غبار", "Dust"},
            {"جدة", "Jeddah"}, {"الرياض", "Riyadh"}, {"بتوقيت", "Time in"},
            {"الأذان بعد", "Adhan in"}, {"الإقامة بعد", "Iqama in"},
            {"القرآن الكريم، بلا إعلانات", "The Quran, without ads"},
            {"القرّاء", "Reciters"}, {"الإذاعات", "Radio stations"},
            {"متابعة التلاوة", "Continue listening"}, {"سورة", "Surah"},
            {"اختر سورة", "Choose a surah"}, {"بث مباشر", "Live"},
            {"جارٍ التحميل…", "Loading…"}, {"تعذر الاتصال", "Could not connect"},
            {"انقطع البث، جارٍ إعادة المحاولة…", "Stream interrupted. Retrying…"},
            {"سيتوقف الصوت بهدوء", "Audio will stop gently"},
            {"إيقاف المؤقت", "Turn off timer"}, {"إيقاف التشغيل الآن", "Stop playback now"},
            {"يتوقف بعد", "Stops in"}, {"مفعّل", "On"}, {"متوقف", "Off"},
            {"الأذان التلقائي", "Automatic Adhan"}, {"الأذان", "Adhan"},
            {"المؤذن", "Muezzin"}, {"اختر المؤذن", "Choose muezzin"},
            {"صوت الأذان", "Adhan volume"},
            {"قد يتأخر الأذان دقائق", "Adhan may be a few minutes late"},
            {"فعّل المنبّهات الدقيقة  •  اختيار يفتح الإعداد", "Enable exact alarms  •  Select to open settings"},
            {"الصوت المختار", "Selected voice"},
            {"اختيار يجرّب ١٢ ثانية ويحفظ الاختيار  •  التسجيلات: أرشيف الإنترنت، ملكية عامة  •  رجوع", "Select to preview 12 seconds and save  •  Recordings: Internet Archive, public domain  •  Back"},
            {"اختيار: تشغيل/إيقاف  •  يمين/يسار تقديم  •  أسفل مؤقت النوم  •  رجوع للقائمة", "Select: play/pause  •  Left/Right: seek  •  Down: sleep timer  •  Back: list"},
            {"اختيار: إيقاف/تشغيل  •  أسفل مؤقت النوم  •  رجوع للقائمة", "Select: stop/play  •  Down: sleep timer  •  Back: list"},
            {"يمين/يسار بين الأقسام  •  اختيار للفتح  •  رجوع للواجهة", "Left/Right: sections  •  Select to open  •  Back: home"},
            {"السابق", "Previous"}, {"التالي", "Next"},
            {"تشغيل أو إيقاف", "Play or pause"}, {"رجوع", "Back"}, {"إيقاف", "Stop"},
            {"تطبيق شاشة التوقف غير مثبّت أو قديم", "The screen saver app is missing or outdated"},
            {"الإدخال الصوتي غير متاح في هذا التلفزيون", "Voice input is unavailable on this TV"},
            {"لا مهام اليوم", "No tasks today"}, {"نعم", "Yes"}, {"لا", "No"},
            {"اختيار على مهمة لإنجازها. أو اضغط «تكلّم» وقل: أضف مهمة … / خلصت … / احذف …", "Select a task to complete it. Or choose Speak to add, finish, or delete a task."},
            {"تكلّم", "Speak"}, {"سمعت:", "Heard:"},
            {"أضيف", "Add"}, {"أعلّم", "Mark"}, {"منجزة", "done"}, {"أحذف", "Delete"},
            {"ما لقيت مهمة تطابق", "No matching task for"},
            {"أكثر من مهمة تطابق", "More than one task matches"},
            {"قل جزءاً أوضح", "Say a more specific part"},
            {"ما فهمت. قل: أضف مهمة … أو خلصت … أو احذف …", "I didn't understand. Say: add a task, finish a task, or delete a task."},
            {"دقيقتين", "2 minutes"}, {"دقائق", "minutes"},
            {"المدينة", "City"}, {"دقيقة", "minutes"}, {" د", " min"},
            {" في ", " in "}, {"، ", ", "}
    };

    static {
        Arrays.sort(WORDS, Comparator.comparingInt((String[] a) -> a[0].length()).reversed());
    }

    public static String of(String source) {
        if (!english || source == null) return source;
        String text = source;
        for (String[] row : WORDS) {
            String left = ARABIC.matcher(row[0].substring(0, 1)).find() ? "(?<![\\p{IsArabic}])" : "";
            String right = ARABIC.matcher(row[0].substring(row[0].length() - 1)).find() ? "(?![\\p{IsArabic}])" : "";
            String bounded = left + Pattern.quote(row[0]) + right;
            text = text.replaceAll(bounded, Matcher.quoteReplacement(row[1]));
        }
        text = text.replaceAll(" ص(?=\\n|$)", " AM").replaceAll(" م(?=\\n|$)", " PM");
        if (ARABIC.matcher(text).find()) text = latinize(text);
        return Digits.of(text);
    }

    private static String latinize(String source) {
        if (Build.VERSION.SDK_INT >= 24) return Icu.INSTANCE.transliterate(source);
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

    public static void init(Context c) {
        SharedPreferences sp = c.getApplicationContext().getSharedPreferences("urtv_language", Context.MODE_PRIVATE);
        english = sp.getBoolean("english", false);
        remember(c, ConfigSnapshot.read(c));
    }

    static boolean remember(Context c, ConfigSnapshot snap) {
        if (snap == null || snap.fragment == null) return false;
        boolean next = snap.english();
        if (next == english) return false;
        english = next;
        c.getApplicationContext().getSharedPreferences("urtv_language", Context.MODE_PRIVATE)
            .edit().putBoolean("english", next).apply();
        return true;
    }
}

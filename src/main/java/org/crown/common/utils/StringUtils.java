package org.crown.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.crown.common.utils.text.StrFormatter;

/**
 * String toolsString tools
 *
 * @author Crown
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * Empty string
     */
    private static final String NULLSTR = "";

    /**
     * Underscore
     */
    private static final char SEPARATOR = '_';
    /**
     * Empty string
     */
    public static final String EMPTY = "";

    /**
     * Determine whether the string is empty (do not exclude the case where the string is'null')
     *
     * @param cs
     * @return boolean
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determine whether the string is empty
     *
     * @param css
     * @return boolean
     */
    public static boolean isNoneBlank(final CharSequence... css) {
        return !isAnyBlank(css);
    }

    /**
     * Determine whether the string is empty
     *
     * @param css
     * @return boolean
     */
    public static boolean isAnyBlank(final CharSequence... css) {
        if (ArrayUtils.isEmpty(css)) {
            return true;
        }
        for (final CharSequence cs : css) {
            if (isBlank(cs)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine whether the string is empty
     *
     * @param cs
     * @return boolean
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * <p>
     * Determine whether the string only has uppercase letters
     * </p>
     *
     * @param str The string to match
     * @return
     */
    public static boolean isUpperCase(String str) {
        return match("^[A-Z]+$", str);
    }

    /**
     * <p>
     * Regular expression matching
     * </p>
     *
     * @param regex Regular expression string
     * @param str   The string to match
     * @return If str conforms to the regular expression format of regex, return true, otherwise return false;
     */
    public static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * <p>
     * Capitalize the first letter of the second string of the concatenated string
     *
     * @param concatStr
     * @param str
     * @return
     */
    public static String concatCapitalize(String concatStr, final String str) {
        if (isBlank(concatStr)) {
            concatStr = EMPTY;
        }
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }

        final char firstChar = str.charAt(0);
        if (Character.isTitleCase(firstChar)) {
            // already capitalized
            return str;
        }

        return new StringBuilder(strLen).append(concatStr).append(Character.toTitleCase(firstChar)).append(str.substring(1))
                .toString();
    }

    /**
     * <p>
     * Capitalize the first letter of the string
     * </p>
     *
     * @param str
     * @return
     */
    public static String capitalize(final String str) {
        return concatCapitalize(null, str);
    }

    /**
     * <p>
     * Determine whether the object is empty
     * </p>
     *
     * @param object
     * @return
     */
    public static boolean checkValNotNull(Object object) {
        if (object instanceof CharSequence) {
            return isNotBlank((CharSequence) object);
        }
        return object != null;
    }

    /**
     * <p>
     * Determine whether the object is empty
     * </p>
     *
     * @param object
     * @return
     */
    public static boolean checkValNull(Object object) {
        return !checkValNotNull(object);
    }

    /**
     * Get object string
     *
     * @param obj
     * @return String
     */
    public static String toString(Object obj) {
        return StringUtils.toString(obj, StringUtils.EMPTY);
    }

    /**
     * Get object string
     *
     * @param obj
     * @return String
     */
    public static String toString(Object obj, String defaults) {
        return obj == null ? defaults : ((StringUtils.EMPTY.equals(obj.toString().trim())) ? defaults : obj.toString()
                .trim());
    }

    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    /**
     * Check whether the given {@code CharSequence} contains actual
     * <em>text</em>.
     * <p>
     * More specifically, this method returns {@code true} if the
     * {@code CharSequence} is not {@code null}, its length is greater than 0,
     * and it contains at least one non-whitespace character.
     * <p>
     * <p>
     * <pre class="code">
     * StringUtils.hasText(null) = false
     * StringUtils.hasText("") = false
     * StringUtils.hasText(" ") = false
     * StringUtils.hasText("12345") = true
     * StringUtils.hasText(" 12345 ") = true
     * </pre>
     *
     * @param str the {@code CharSequence} to check (may be {@code null})
     * @return {@code true} if the {@code CharSequence} is not {@code null}, its
     * length is greater than 0, and it does not contain whitespace only
     * @see Character#isWhitespace
     */
    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check that the given {@code String} is neither {@code null} nor of length
     * 0.
     * <p>
     * Note: this method returns {@code true} for a {@code String} that purely
     * consists of whitespace.
     *
     * @param str the {@code String} to check (may be {@code null})
     * @return {@code true} if the {@code String} is not {@code null} and has
     * length
     * @see #hasLength(CharSequence)
     * @see #hasText(CharSequence)
     */
    public static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }

    /**
     * Determine whether the object is empty
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    /**
     * Determine whether the object is not empty
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }

    /**
     * <p>Splits the provided text into an array, separators specified.
     * This is an alternative to using StringTokenizer.</p>
     * <p>
     * <p>The separator is not included in the returned String array.
     * Adjacent separators are treated as one separator.
     * For more control over the split use the StrTokenizer class.</p>
     * <p>
     * <p>A {@code null} input String returns {@code null}.
     * A {@code null} separatorChars splits on whitespace.</p>
     * <p>
     * <pre>
     * StringUtils.split(null, *)         = null
     * StringUtils.split("", *)           = []
     * StringUtils.split("abc def", null) = ["abc", "def"]
     * StringUtils.split("abc def", " ")  = ["abc", "def"]
     * StringUtils.split("abc  def", " ") = ["abc", "def"]
     * StringUtils.split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
     * </pre>
     *
     * @param str            the String to parse, may be null
     * @param separatorChars the characters used as the delimiters,
     *                       {@code null} splits on whitespace
     * @return an array of parsed Strings, {@code null} if null String input
     */
    public static String[] split2Array(final String str, final String separatorChars) {
        List<String> list = split2List(str, separatorChars);
        return list.toArray(new String[0]);
    }

    public static String[] split2Array(final String str) {
        return split2Array(str, ",");
    }

    public static List<String> split2List(final String str, final String separatorChars) {
        return splitWorker(str, separatorChars, -1, false);
    }

    public static List<String> split2List(final String str) {
        return split2List(str, ",");
    }

    public static <T> List<T> split2List(final String str, Function<? super Object, T> mapper) {
        return split2List(str, ",", mapper);
    }

    public static <T> List<T> split2List(final String str, final String separatorChars, Function<? super Object, T> mapper) {
        return split2List(str, separatorChars).stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * Performs the logic for the {@code split} and
     * {@code splitPreserveAllTokens} methods that return a maximum array
     * length.
     *
     * @param str               the String to parse, may be {@code null}
     * @param separatorChars    the separate character
     * @param max               the maximum number of elements to include in the
     *                          array. A zero or negative value implies no limit.
     * @param preserveAllTokens if {@code true}, adjacent separators are
     *                          treated as empty token separators; if {@code false}, adjacent
     *                          separators are treated as one separator.
     * @return an array of parsed Strings, {@code null} if null String input
     */
    private static List<String> splitWorker(final String str, final String separatorChars, final int max, final boolean preserveAllTokens) {
        // Performance tuned for 2.0 (JDK1.4)
        // Direct code is quicker than StringTokenizer.
        // Also, StringTokenizer uses isSpace() not isWhitespace()

        if (str == null) {
            return null;
        }
        final int len = str.length();
        if (len == 0) {
            return Collections.emptyList();
        }
        final List<String> list = new ArrayList<>();
        int sizePlus1 = 1;
        int i = 0, start = 0;
        boolean match = false;
        boolean lastMatch = false;
        if (separatorChars == null) {
            // Null separator means use whitespace
            while (i < len) {
                if (Character.isWhitespace(str.charAt(i))) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        } else if (separatorChars.length() == 1) {
            // Optimise 1 character case
            final char sep = separatorChars.charAt(0);
            while (i < len) {
                if (str.charAt(i) == sep) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        } else {
            // standard case
            while (i < len) {
                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        }
        if (match || preserveAllTokens && lastMatch) {
            list.add(str.substring(start, i));
        }
        return list;
    }

    /**
     * <p>
     * Whether it is CharSequence type
     * </p>
     *
     * @param object
     * @return
     */
    public static Boolean isCharSequence(Object object) {
        return Objects.isNull(object) ? false : isCharSequence(object.getClass());
    }

    /**
     * <p>
     * Whether it is CharSequence type
     * </p>
     *
     * @param cls
     * @return
     */
    public static Boolean isCharSequence(Class<?> cls) {
        return cls != null && CharSequence.class.isAssignableFrom(cls);
    }

    /**
     * Get parameter is not null
     *
     * @param value defaultValue Value to be determined
     * @return value return value
     */
    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * * Determine whether a Collection is empty, including List, Set, Queue
     *
     * @param coll Collection to be determined
     * @return true: empty, false: not empty
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * * Determine whether a Collection is not empty, including List, Set, Queue
     *
     * @param coll Collection to be determined
     * @return true: not empty, false: empty
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * * Determine whether an object array is empty
     *
     * @param objects The array of objects to be determined
     *                * @return true: empty, false: not empty
     */
    public static boolean isEmpty(Object[] objects) {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * * Determine whether an object array is not empty
     *
     * @param objects The array of objects to be determined
     * @return true：not empty, false：empty
     */
    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    /**
     * * Determine whether a Map is empty
     *
     * @param map Map to be determined
     * @return true：empty, false：not empty
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * * Determine whether a Map is not empty
     *
     * @param map Map to be determined
     * @return true：not empty, false：empty
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * * Determine whether a string is empty
     *
     * @param str String
     * @return true: empty, false: not empty
     */
    public static boolean isEmpty(String str) {
        return isNull(str) || NULLSTR.equals(str.trim());
    }

    /**
     * * Determine whether a string is not empty
     *
     * @param str String
     * @return true：not empty, false：empty
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * * Determine whether an object is empty
     *
     * @param object Object
     * @return true：empty, false：not empty
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * * Determine whether an object is not empty
     *
     * @param object Object
     * @return true：not empty, false：empty
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * * Determine whether an object is an array type (array of basic Java types)
     *
     * @param object object
     * @return true: it is an array, false: it is not an array
     */
    public static boolean isArray(Object object) {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * Go to spaces
     */
    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * Intercept string
     *
     * @param str   String
     * @param start Start
     * @return result
     */
    public static String substring(final String str, int start) {
        if (str == null) {
            return NULLSTR;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return NULLSTR;
        }

        return str.substring(start);
    }

    /**
     * Intercept string
     *
     * @param str   String
     * @param start Start
     * @param end   End
     * @return result
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return NULLSTR;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return NULLSTR;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * Formatted text, {} means placeholder<br>
     * This method simply replaces the placeholder {} with parameters in order<br>
     * If you want to output {}, use \\ to escape {, if you want to output the \ before {}, use the double escape character \\\\<br>
     * Example：<br>
     * Often used：format("this is {} for {}", "a", "b") -> this is a for b<br>
     * Escape{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * Escape\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     *
     * @param template Text template, the replaced part is represented by {}
     * @param params   Parameter value
     * @return Formatted text
     */
    public static String format(String template, Object... params) {
        if (isEmpty(params) || isEmpty(template)) {
            return template;
        }
        return StrFormatter.format(template, params);
    }

    /**
     * UnderScore to naming
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // Whether the preceding character is uppercase
        boolean preCharIsUpperCase;
        // Whether the current character is uppercase
        boolean curreCharIsUpperCase;
        // Whether the next character is uppercase
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append(SEPARATOR);
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * Does it contain a string
     *
     * @param str  Verification string
     * @param strs String group
     * @return Contains returns true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Convert strings named in underscore uppercase to camel case。If the string named in underscore capitalization before conversion is empty, an empty string is returned. For example: HELLO_WORLD->HelloWorld
     *
     * @param name The string named in underscore capitalization before conversion
     * @return Converted to camel case named string
     */
    public static String convertToCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        // Quick check
        if (name == null || name.isEmpty()) {
            // No need to switch
            return "";
        } else if (!name.contains("_")) {
            // No underscore, capitalize only the first letter
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // Split the original string with underscores
        String[] camels = name.split("_");
        for (String camel : camels) {
            // Skip the underline or double underline at the beginning and end of the original string
            if (camel.isEmpty()) {
                continue;
            }
            // Capitalize the first letter
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * CamelCase nomenclature For example: username->userName
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
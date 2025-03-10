package by.it.ena.jd01_06;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TaskA2 {

    private static String[] w = {};
    private static int[] count = {};

    private static int pos(String word) {
        for (int i = 0; i < w.length; i++) {
            if (w[i].equals(word))
                return i;
        }
        return -1;
    }


    public static void main(String[] args) {
       // StringBuilder stringBuilder=new StringBuilder(Poem.text); почему здеь не делали сб?
        Pattern pattern = Pattern.compile("[а-яА-ЯёЁ]+");
        Matcher matcher = pattern.matcher(Poem.text);
        while (matcher.find()) {
            String word = matcher.group();
            int p = pos(word);
            if (p >= 0) {
                count[p]++;
            } else {
                int last = w.length;
                w = Arrays.copyOf(w, last + 1);
                w[last] = word;
                count = Arrays.copyOf(count, last + 1);
                count[last] = 1;
            }
        }
        for (int i = 0; i < w.length; i++) {
            System.out.println(w[i] + "=" + count[i]);
        }
    }
}

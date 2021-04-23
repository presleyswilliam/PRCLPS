package eightqueens;

import java.util.Map;
import java.util.Map.Entry;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

public class MapUtil {
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static <K, V extends Comparable<? super V>> List<Integer> mapToList(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        
        List<Integer> result = new ArrayList<Integer>();

        for (Entry<K, V> entry : list) {
            // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            result.add((int) entry.getKey());
        }

        return result;
    } 
}

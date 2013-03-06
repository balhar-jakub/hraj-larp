package cz.hrajlarp.model;

import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class CaseInsensitiveComparator implements Comparator<String> {
    public int compare(String s1, String s2) {
        assert s1 != null && s2 != null;
        return String.CASE_INSENSITIVE_ORDER.compare(s1, s2);
    }
}

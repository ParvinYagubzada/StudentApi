package az.code.springweb.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class Paging<T> {
    private List<T> data;
    private int pageCount;
    private int pageIndex;
    private int itemCount;
    private String nextUrl = null;
    private String previousUrl = null;

    public Paging(List<T> data, int pageIndex, int limit, String url) {
        int size = data.size();
        this.itemCount = limit + (pageIndex * limit);
        this.pageCount = (int) Math.ceil(size / (double) limit);
        this.pageIndex = pageIndex;
        this.data = data.stream()
                .skip((long) pageIndex * limit)
                .limit(limit)
                .collect(Collectors.toList());
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf('&') + 1);
        } else {
            url += '?';
        }
        if (itemCount < data.size())
            this.nextUrl = url + "limit=" + limit + "&pageIndex=" + (pageIndex + 1);
        if (pageIndex > 0)
            this.previousUrl = url + "limit=" + limit + "&pageIndex=" + (pageIndex - 1);
    }
}

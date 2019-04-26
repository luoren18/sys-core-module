package top.luoren.basis.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author luoren
 * @date 2019-04-23 17:16
 */
public class Query<T> extends LinkedHashMap<String, String> {
    private static final long serialVersionUID = 1L;
    private static final String PAGE = "page";
    private static final String LIMIT = "limit";
    private static final String DESC_FILED = "desc_field";
    private static final String ASC_FIELD = "asc_field";
    /**
     * mybatis-plus分页参数
     */
    private Page<T> page;
    /**
     * 当前页码
     */
    private int currPage = 1;
    /**
     * 每页条数
     */
    private int limit = 10;

    public Query(Map<String, String> params) {
        if (!CollectionUtils.isEmpty(params)) {
            this.putAll(params);

            //分页参数
            if (!StringUtils.isEmpty(params.get(PAGE))) {
                currPage = Integer.parseInt(params.get(PAGE));
            }
            if (!StringUtils.isEmpty(params.get(LIMIT))) {
                limit = Integer.parseInt(params.get(LIMIT));
            }
        }

        this.put("offset", ((currPage - 1) * limit) + "");
        this.put("page", currPage + "");
        this.put("limit", limit + "");
        this.page = new Page<>(currPage, limit);
    }

    public Page<T> getPage() {
        return page;
    }

    public int getCurrPage() {
        return currPage;
    }

    public int getLimit() {
        return limit;
    }
}


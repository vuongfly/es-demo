package vuong.fly.esdemo.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;
import vuong.fly.esdemo.helper.Indices;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Component
public class PersonQueryBuilder {

    private ObjectMapper mapper = new ObjectMapper();

    private RestHighLevelClient client;

    public PersonQueryBuilder() {
        ClientConfiguration clientConfiguration =
                ClientConfiguration.builder().connectedTo("localhost:9200").build();
        client = RestClients.create(clientConfiguration).rest();
    }

    public NativeSearchQuery findByName(String name) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(name, "name"))
                .build();
        System.out.println(searchQuery);
        return searchQuery;
    }

    public QueryBuilder findByAge(int from, int to) throws IOException {

        return QueryBuilders.rangeQuery("age").from(from).to(to);
    }

    public Object executeQueryBuilder(String index, QueryBuilder... queryBuilder) throws IOException {

        SearchSourceBuilder builder = new SearchSourceBuilder();
        for (QueryBuilder qb : queryBuilder) {
            builder.postFilter(qb);
        }
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
        searchRequest.source(builder);
        searchRequest.indices(index);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        // Get _source
        var collect = Arrays.stream(response.getHits().getHits())
                .collect(Collectors.toList())
                .stream().map(SearchHit::getSourceAsMap)
//                .collect(Collectors.toList())
                ;

        return collect;
    }


}

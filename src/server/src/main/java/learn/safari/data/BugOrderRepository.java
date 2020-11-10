package learn.safari.data;

import learn.safari.models.BugOrder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BugOrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public BugOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BugOrder> findAll() {
        return jdbcTemplate.query("select bug_order_id, `name`, `description` from bug_order;", (resultSet, rowNum) -> {
            BugOrder order = new BugOrder();
            order.setBugOrderId(resultSet.getInt("bug_order_id"));
            order.setName(resultSet.getString("name"));
            order.setDescription(resultSet.getString("description"));
            return order;
        });
    }
}

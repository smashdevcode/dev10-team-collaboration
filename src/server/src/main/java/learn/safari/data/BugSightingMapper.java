package learn.safari.data;

import learn.safari.models.BugOrder;
import learn.safari.models.BugSighting;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BugSightingMapper implements RowMapper<BugSighting> {

    @Override
    public BugSighting mapRow(ResultSet resultSet, int i) throws SQLException {

        BugSighting sighting = new BugSighting();
        sighting.setSightingId(resultSet.getInt("sighting_id"));
        sighting.setBugType(resultSet.getString("bug_type"));
        sighting.setDescription(resultSet.getString("description"));
        sighting.setDate(resultSet.getDate("sighting_date").toLocalDate());
        sighting.setInterest(resultSet.getDouble("interest"));
        sighting.setImageUrl(resultSet.getString("image_url"));

        BugOrder order = new BugOrder();
        order.setBugOrderId(resultSet.getInt("bug_order_id"));
        order.setName(resultSet.getString("name"));
        order.setDescription(resultSet.getString("order_description"));

        sighting.setOrder(order);
        return sighting;
    }
}

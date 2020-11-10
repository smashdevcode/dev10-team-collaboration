package learn.safari.data;

import learn.safari.models.BugSighting;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class BugSightingRepository {

    private final JdbcTemplate jdbcTemplate;

    public BugSightingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BugSighting> findAll() {

        final String sql = "select s.sighting_id, s.bug_type, s.description, s.sighting_date, s.interest, s.image_url, "
                + "s.bug_order_id, o.name, o.description order_description "
                + "from sighting s "
                + "inner join bug_order o on s.bug_order_id = o.bug_order_id;";

        return jdbcTemplate.query(sql, new BugSightingMapper());
    }

    public BugSighting findById(int sightingId) {

        final String sql = "select s.sighting_id, s.bug_type, s.description, s.sighting_date, s.interest, s.image_url, "
                + "s.bug_order_id, o.name, o.description order_description "
                + "from sighting s "
                + "inner join bug_order o on s.bug_order_id = o.bug_order_id "
                + "where s.sighting_id = ?;";

        return jdbcTemplate.query(sql, new BugSightingMapper(), sightingId)
                .stream().findFirst().orElse(null);
    }

    public BugSighting add(BugSighting sighting) {

        final String sql = "insert into sighting "
                + "(bug_type, description, sighting_date, interest, image_url, bug_order_id) "
                + "values (?,?,?,?,?,?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, sighting.getBugType());
            ps.setString(2, sighting.getDescription());
            ps.setDate(3, Date.valueOf(sighting.getDate()));
            ps.setDouble(4, sighting.getInterest());
            ps.setString(5, sighting.getImageUrl());
            ps.setInt(6, sighting.getOrder().getBugOrderId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        sighting.setSightingId(keyHolder.getKey().intValue());

        return sighting;
    }

    public boolean update(BugSighting sighting) {

        final String sql = "update sighting set "
                + "bug_type = ?, "
                + "description = ?, "
                + "sighting_date = ?, "
                + "interest = ?, "
                + "image_url = ?, "
                + "bug_order_id = ? "
                + "where sighting_id = ?;";

        return jdbcTemplate.update(sql,
                sighting.getBugType(),
                sighting.getDescription(),
                sighting.getDate(),
                sighting.getInterest(),
                sighting.getImageUrl(),
                sighting.getOrder().getBugOrderId(),
                sighting.getSightingId()) > 0;
    }

    public boolean deleteById(int sightingId) {
        final String sql = "delete from sighting where sighting_id = ?";
        return jdbcTemplate.update(sql, sightingId) > 0;
    }
}

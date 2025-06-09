package com.example.transaction.required.dao;

import com.example.transaction.required.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertUser(String name) {
        String sql = "INSERT INTO USERS (NAME) VALUES (?)";
        jdbcTemplate.update(sql, name);
    }

    @Override
    public List<User> findUserByName(String name) { // 返回 List<User>
        String sql = "SELECT ID, NAME FROM USERS WHERE NAME = ?";
        // 使用 query() 替代 queryForObject()，返回列表（空列表表示无数据）
        return jdbcTemplate.query(sql, new UserRowMapper(), name);
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("ID"));
            user.setName(rs.getString("NAME"));
            return user;
        }
    }
}
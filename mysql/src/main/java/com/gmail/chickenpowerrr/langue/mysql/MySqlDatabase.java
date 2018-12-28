package com.gmail.chickenpowerrr.langue.mysql;

import com.gmail.chickenpowerrr.langue.core.language.ResourceLanguage;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MySqlDatabase {

    private final HikariDataSource dataSource = new HikariDataSource();

    public MySqlDatabase(LanguageResourceCredentials credentials) {
        this.dataSource.setMaximumPoolSize(credentials.getInt("max_pool_size"));
        this.dataSource.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        this.dataSource.addDataSourceProperty("serverName", credentials.getString("host"));
        this.dataSource.addDataSourceProperty("port", credentials.getInt("port"));
        this.dataSource.addDataSourceProperty("databaseName", credentials.getString("database"));
        this.dataSource.addDataSourceProperty("user", credentials.getString("username"));
        this.dataSource.addDataSourceProperty("password", credentials.getString("password"));
        this.dataSource.addDataSourceProperty("useSSL", false);

        try(Connection connection = this.dataSource.getConnection();
            Statement statement = connection.createStatement()) {
            statement.execute("create table if not exists language" +
                    "(" +
                    "  id   int unsigned auto_increment" +
                    "    primary key," +
                    "  name varchar(64) not null," +
                    "  constraint language_name_uindex" +
                    "  unique (name)" +
                    ");");

            statement.execute("create table if not exists translation_key" +
                    "(" +
                    "  id    int unsigned auto_increment" +
                    "    primary key," +
                    "  `key` varchar(64) not null," +
                    "  constraint sentence_key_key_uindex" +
                    "  unique (`key`)" +
                    ");");

            statement.execute("create table if not exists translations" +
                    "(" +
                    "  id                 int unsigned auto_increment" +
                    "    primary key," +
                    "  language_id        int unsigned not null," +
                    "  translation_key_id int unsigned not null," +
                    "  translation        mediumtext   not null," +
                    "  constraint translations_language_id_fk" +
                    "  foreign key (language_id) references language (id)" +
                    "    on update cascade" +
                    "    on delete cascade," +
                    "  constraint translations_sentence_key_id_fk" +
                    "  foreign key (translation_key_id) references translation_key (id)" +
                    "    on update cascade" +
                    "    on delete cascade" +
                    ");");
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, ResourceLanguage> getLanguages() {
        Map<String, Map<String, String>> resourceLanguages = new HashMap<>();

        try(Connection connection = this.dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT tk.key, l.name, t.translation " +
                    "FROM translations t " +
                    "JOIN language l ON t.language_id = l.id " +
                    "JOIN translation_key tk ON t.translation_key_id = tk.id")) {

            while(resultSet.next()) {
                String name = resultSet.getString("name");
                if(!resourceLanguages.containsKey(name)) {
                    resourceLanguages.put(name, new HashMap<>());
                }

                resourceLanguages.get(name).put(resultSet.getString("key"),
                        resultSet.getString("translation"));
            }

            return resourceLanguages.entrySet().stream()
                    .map(entry -> new HashMap.SimpleEntry<>(entry.getKey(),
                            new ResourceLanguage(entry.getKey(), resourceLanguages.get(entry.getKey()))))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

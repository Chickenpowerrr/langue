package com.gmail.chickenpowerrr.langue.mysql.test;

import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderManager;
import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderPattern;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceFactory;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class MySqlTest {

    private final LanguageResourceCredentials credentials = new LanguageResourceCredentials()
            .addProperty("max_pool_size", 10)
            .addProperty("host", "127.0.0.1")
            .addProperty("port", 3306)
            .addProperty("database", "langue")
            .addProperty("username", "root")
            .addProperty("password", "walrus");

    @Test
    public void connectTest() {
        createTables();
    }

    @Test
    public void tableExistTest() {
        createTables();
        HikariDataSource dataSource = getDataSource();

        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SHOW TABLES;")) {

            Collection<String> tables = new HashSet<String>() {{
                add("language");
                add("translation_key");
                add("translations");
            }};

            while(resultSet.next()) {
                tables.remove(resultSet.getString(1));
            }
            assertEquals(0, tables.size());
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void selectTest() {
        createTables();

        HikariDataSource dataSource = getDataSource();

        Collection<String> languages = new HashSet<>();
        Collection<String> translationKeys = new HashSet<>();

        for(int i = 0; i < 5; i++) {
            languages.add(UUID.randomUUID().toString());
            translationKeys.add(UUID.randomUUID().toString());
        }

        try(Connection connection = dataSource.getConnection();
            PreparedStatement insertLanguage = connection.prepareStatement(
                    "INSERT INTO language (name) VALUES (?);");
            PreparedStatement insertKey = connection.prepareStatement(
                    "INSERT INTO translation_key (`key`) VALUES (?);");
            PreparedStatement insertTranslation = connection.prepareStatement(
                    "INSERT INTO translations (language_id, translation_key_id, translation) VALUES " +
                            "((SELECT id FROM language WHERE name = ?), " +
                            "(SELECT id FROM translation_key WHERE `key` = ?), ?)");
            PreparedStatement deleteLanguage = connection.prepareStatement("DELETE FROM language WHERE name = ?;");
            PreparedStatement deleteKey = connection.prepareStatement(
                    "DELETE FROM translation_key WHERE `key` = ?;")) {

            for(String language : languages) {
                insertLanguage.setString(1, language);
                insertLanguage.execute();
            }

            for(String translationKey : translationKeys) {
                insertKey.setString(1, translationKey);
                insertKey.execute();

                for(String language : languages) {
                    insertTranslation.setString(1, language);
                    insertTranslation.setString(2, translationKey);
                    insertTranslation.setString(3, "This is a test value");
                    insertTranslation.execute();
                }
            }

            LanguageResource languageResource = new LanguageResourceFactory()
                    .getLanguageResource("MySQL", new PlaceholderManager(
                            new PlaceholderPattern("%%", "%%")), this.credentials);

            for(String language : languages) {
                for(String translationKey : translationKeys) {
                    assertEquals("This is a test value", languageResource.getMessage(
                            language, translationKey));
                    deleteLanguage.setString(1, language);
                    deleteKey.setString(1, translationKey);
                }
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private HikariDataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setMaximumPoolSize(credentials.getInt("max_pool_size"));
        dataSource.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        dataSource.addDataSourceProperty("serverName", credentials.getString("host"));
        dataSource.addDataSourceProperty("port", credentials.getInt("port"));
        dataSource.addDataSourceProperty("databaseName", credentials.getString("database"));
        dataSource.addDataSourceProperty("user", credentials.getString("username"));
        dataSource.addDataSourceProperty("password", credentials.getString("password"));
        dataSource.addDataSourceProperty("useSSL", false);

        return dataSource;
    }

    private void createTables() {
        new LanguageResourceFactory().getLanguageResource("MySQL",
                new PlaceholderManager(new PlaceholderPattern("%%", "%%")), this.credentials);
    }
}

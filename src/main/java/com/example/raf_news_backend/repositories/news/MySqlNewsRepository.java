package com.example.raf_news_backend.repositories.news;
import com.example.raf_news_backend.entities.Comment;
import com.example.raf_news_backend.entities.News;
import com.example.raf_news_backend.entities.Tag;
import com.example.raf_news_backend.repositories.MySqlAbstractRepository;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MySqlNewsRepository extends MySqlAbstractRepository implements INewsRepository {

    @Override
    public News addNews(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement
                    ("INSERT INTO news (title, text, category, dateCreated, numberOfVisits, authorEmail, tags) VALUES(?, ?, ?, ?, ?, ?, ?)"
                            , generatedColumns);
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getText());
            preparedStatement.setString(3, news.getCategory());

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

            preparedStatement.setString(4, formatter.format(new Date()));
            preparedStatement.setInt(5, 0);
            preparedStatement.setString(6, news.getAuthorEmail());
            preparedStatement.setString(7, news.getTags());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                news.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public List<News> allNews() {
        List<News> news = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM news");
            while (resultSet.next()) {
                News n = new News();
                n.setId(resultSet.getInt("id"));
                n.setTitle(resultSet.getString("title"));
                n.setText(resultSet.getString("text"));
                n.setCategory(resultSet.getString("category"));
                n.setDateCreated(resultSet.getString("dateCreated"));
                n.setNumberOfVisits(resultSet.getInt("numberOfVisits"));
                n.setAuthorEmail(resultSet.getString("authorEmail"));
                n.setTags(resultSet.getString("tags"));
                n.setTagList(this.parseTagStringToList(n.getTags()));

                n.setComments(this.getCommentsByNewsId(n.getId()));

                news.add(n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return news;
    }

    @Override
    public News findNews(Integer id) {
        News news = new News();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {

                Integer newsId = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String text = resultSet.getString("text");
                String category = resultSet.getString("category");
                String dateCreated = resultSet.getString("dateCreated");
                Integer numberOfVisits = resultSet.getInt("numberOfVisits");
                String authorEmail = resultSet.getString("authorEmail");
                String tags = resultSet.getString("tags");

                news.setId(newsId);
                news.setTitle(title);
                news.setText(text);
                news.setCategory(category);
                news.setDateCreated(dateCreated);
                news.setNumberOfVisits(numberOfVisits);
                news.setAuthorEmail(authorEmail);
                news.setTags(tags);
                news.setTagList(this.parseTagStringToList(news.getTags()));

                news.setComments(this.getCommentsByNewsId(news.getId()));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return news;
    }

    @Override
    public void deleteNews(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM news WHERE id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public List<News> findAllNewsByCategoryName(String categoryName) {
        List<News> news = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news where category = ?");
            preparedStatement.setString(1, categoryName);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                News n = new News();
                n.setId(resultSet.getInt("id"));
                n.setTitle(resultSet.getString("title"));
                n.setText(resultSet.getString("text"));
                n.setCategory(resultSet.getString("category"));
                n.setDateCreated(resultSet.getString("dateCreated"));
                n.setNumberOfVisits(resultSet.getInt("numberOfVisits"));
                n.setAuthorEmail(resultSet.getString("authorEmail"));
                n.setTags(resultSet.getString("tags"));
                n.setTagList(this.parseTagStringToList(n.getTags()));

                n.setComments(this.getCommentsByNewsId(n.getId()));

                news.add(n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return news;
    }

    @Override
    public void updateCategoryName(String oldCategoryName, String newCategoryName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE news SET category=? WHERE category=? ");
            preparedStatement.setString(1, newCategoryName);
            preparedStatement.setString(2, oldCategoryName);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public void updateNews(Integer id, String title, String category, String tags, String text) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE news SET title=?, category=?, text=?, tags=? WHERE id=? ");
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, category);
            preparedStatement.setString(3, text);
            preparedStatement.setString(4, tags);
            preparedStatement.setInt(5, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public Comment addComment(Integer newsId, Comment comment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement
                    ("INSERT INTO comments (newsId, author, text, dateCreated) VALUES(?, ?, ?, ?)", generatedColumns);
            preparedStatement.setInt(1, newsId);
            preparedStatement.setString(2, comment.getAuthor());
            preparedStatement.setString(3, comment.getText());
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            preparedStatement.setString(4, formatter.format(new Date()));
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                comment.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return comment;
    }

    @Override
    public List<News> findAllSimilarNewsByKeyword(String keyword) {
        List<News> res = new ArrayList<>();
        for(News n: this.allNews()){
            for(Tag t: n.getTagList()){
                if(t.getKeyWords().contains(keyword)){
                    res.add(n);
                }
            }
        }
        return res;
    }

    private List<Comment> getCommentsByNewsId(Integer newsId){
        List<Comment> comments = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM comments where newsId = ?");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Comment c = new Comment();
                c.setId(resultSet.getInt("id"));
                c.setAuthor(resultSet.getString("author"));
                c.setText(resultSet.getString("text"));
                c.setDateCreated(resultSet.getString("dateCreated"));
                comments.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return comments;
    }
    private List<Tag> parseTagStringToList(String tags){
        if(tags == null || tags == ""){
            return new ArrayList<Tag>();
        }
        String[] tagsArray = tags.split(",");
        List<Tag> tagList = new ArrayList<>();
        Tag t = new Tag();
        t.setKeyWords(Arrays.asList(tagsArray));
        tagList.add(t);
        return tagList;
    }
}

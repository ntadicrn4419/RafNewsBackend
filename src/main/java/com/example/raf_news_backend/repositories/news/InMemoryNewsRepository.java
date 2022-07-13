package com.example.raf_news_backend.repositories.news;
import com.example.raf_news_backend.entities.Comment;
import com.example.raf_news_backend.entities.News;
import com.example.raf_news_backend.entities.Tag;
import com.example.raf_news_backend.repositories.user.InMemoryUserRepository;
import com.example.raf_news_backend.services.UserService;
import javassist.Loader;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryNewsRepository implements INewsRepository{
    public static List<News> newsList = new CopyOnWriteArrayList<>();

    static {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        News n1 = new News();

        n1.setId(1);
        n1.setTitle("Novak Djokovic osvojio Wimbledon!");
        n1.setText("gas gas gas gascina");
        n1.setDateCreated(formatter.format(new Date()));
        n1.setNumberOfVisits(0);
        n1.setAuthorEmail(InMemoryUserRepository.USERS.get(0).getEmail());
        n1.setComments(new ArrayList<>());
        n1.setTagList(new ArrayList<>());
        //n1.setCategory(InMemoryCategoryRepository.categoryList.get(0));
        n1.setCategory("Sport");

        News n2 = new News();

        n2.setId(2);
        n2.setTitle("Politka-vest-nesto-bezveze");
        n2.setText("dfnoAFNASKDLFNSDKL");
        n2.setDateCreated("04-07-2022");
        n2.setNumberOfVisits(0);
        n2.setAuthorEmail(InMemoryUserRepository.USERS.get(0).getEmail());
        n2.setComments(new ArrayList<>());
        n2.setTagList(new ArrayList<>());
        //n2.setCategory(InMemoryCategoryRepository.categoryList.get(1));
        n2.setCategory("Politika");

        News n3 = new News();

        n3.setId(3);
        n3.setTitle("Politka2-vest2-nesto2-bezveze2");
        n3.setText("dfnoAFNASKDLFNSDKL");
        n3.setDateCreated("09-07-2022");
        n3.setNumberOfVisits(0);
        n3.setAuthorEmail(InMemoryUserRepository.USERS.get(0).getEmail());
        n3.setComments(new ArrayList<>());
        n3.setTagList(new ArrayList<>());
        //n3.setCategory(InMemoryCategoryRepository.categoryList.get(1));
        n3.setCategory("Politika");

        News n4 = new News();

        n4.setId(4);
        n4.setTitle("Politka3-vest3-nesto3-bezveze3");
        n4.setText("dfnoAFNASKDLFNSDKL");
        n4.setDateCreated("02-07-2022");
        n4.setNumberOfVisits(0);
        n4.setAuthorEmail(InMemoryUserRepository.USERS.get(0).getEmail());
        n4.setComments(new ArrayList<>());

        List<Tag> tagList = new ArrayList<>();
        List<String> keywords = new ArrayList<>();
        keywords.add("politika");
        keywords.add("ludilo mozga");
        keywords.add("bezveze");
        Tag t = new Tag(1,keywords);
        tagList.add(t);

        n4.setTagList(tagList);
        n4.setCategory("Politika");

        newsList.add(n1);
        newsList.add(n2);
        newsList.add(n3);
        newsList.add(n4);

    }

    @Override
    public synchronized News addNews(News news) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        int id = newsList.size();
        news.setId(id+1);
        news.setDateCreated(formatter.format(new Date()));
        news.setNumberOfVisits(0);
        news.setAuthorEmail(UserService.currentUser.getEmail());
        if(news.getTags() != null && !news.getTags().isEmpty()){
            news.setTagList(parseTagStringToList(news.getTags()));
        }
        news.setComments(new ArrayList<>());
        newsList.add(Math.toIntExact(id), news);

        return news;
    }

    @Override
    public List<News> allNews() {
        return new ArrayList<>(newsList);
    }

    @Override
    public News findNews(Integer id) {
        for(News n: newsList){
            if(n.getId().equals(id)){
                return n;
            }
        }
        return null;
    }

    @Override
    public void deleteNews(Integer id) {
        newsList.removeIf(n -> n.getId().equals(id));
    }

    @Override
    public List<News> findAllNewsByCategoryName(String categoryName) {
        List<News> result = new ArrayList<>();
        for(News n: newsList){
            if(n.getCategory().equals(categoryName)){
                result.add(n);
            }
        }
        return result;
    }

    @Override
    public void updateNews(Integer id, String title, String category, String tags, String text) {
        for(News n: newsList){
            if(n.getId().equals(id)){
                n.setTitle(title);
                n.setCategory(category);
                if(tags != null && !tags.equals("")){
                    n.setTagList(parseTagStringToList(tags));
                }
                n.setText(text);
                return;
            }
        }
    }

    @Override
    public void updateCategoryName(String oldCategoryName, String newCategoryName) {
        for(News n: newsList){
            if(n.getCategory().equals(oldCategoryName)){
                n.setCategory(newCategoryName);
            }
        }
    }

    @Override
    public Comment addComment(Integer newsId, Comment comment) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        comment.setDateCreated(formatter.format(new Date()));
        for(News n: newsList){
            if(n.getId().equals(newsId)){
                n.getComments().add(comment);
                break;
            }
        }
        return comment;
    }

    @Override
    public List<News> findAllSimilarNewsByKeyword(String keyword) {
        Set<News> set = new HashSet<>();
        for(News n: newsList){
            for(Tag t: n.getTagList()){
                for(String s: t.getKeyWords()){
                    if(s.equalsIgnoreCase(keyword)){
                        set.add(n);
                        break;
                    }
                }
            }
        }
        return new ArrayList<>(set);
    }

    List<Tag> parseTagStringToList(String tags){
        String[] tagsArray = tags.split(",");
        List<Tag> tagList = new ArrayList<>();
        Tag t = new Tag();
        t.setKeyWords(Arrays.asList(tagsArray));
        tagList.add(t);
        return tagList;
    }
}

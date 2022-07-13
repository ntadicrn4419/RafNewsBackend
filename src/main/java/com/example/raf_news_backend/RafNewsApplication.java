package com.example.raf_news_backend;

import com.example.raf_news_backend.repositories.categories.ICategoryRepository;
import com.example.raf_news_backend.repositories.categories.InMemoryCategoryRepository;
import com.example.raf_news_backend.repositories.categories.MySqlCategoryRepository;
import com.example.raf_news_backend.repositories.news.INewsRepository;
import com.example.raf_news_backend.repositories.news.InMemoryNewsRepository;
import com.example.raf_news_backend.repositories.news.MySqlNewsRepository;
import com.example.raf_news_backend.repositories.user.IUserRepository;
import com.example.raf_news_backend.repositories.user.InMemoryUserRepository;
import com.example.raf_news_backend.repositories.user.MySqlUserRepository;
import com.example.raf_news_backend.services.CategoryService;
import com.example.raf_news_backend.services.NewsService;
import com.example.raf_news_backend.services.UserService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;


@ApplicationPath("/api")
public class RafNewsApplication extends ResourceConfig {

    public RafNewsApplication(){
        // Ukljucujemo validaciju
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        // Definisemo implementacije u dependency container-u
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlNewsRepository.class).to(INewsRepository.class).in(Singleton.class);
                //this.bind(InMemoryNewsRepository.class).to(INewsRepository.class).in(Singleton.class);
                this.bind(MySqlUserRepository.class).to(IUserRepository.class).in(Singleton.class);
                //this.bind(InMemoryUserRepository.class).to(IUserRepository.class).in(Singleton.class);
                this.bind(MySqlCategoryRepository.class).to(ICategoryRepository.class).in(Singleton.class);
                //this.bind(InMemoryCategoryRepository.class).to(ICategoryRepository.class).in(Singleton.class);

                this.bindAsContract(CategoryService.class);
                this.bindAsContract(NewsService.class);
                this.bindAsContract(UserService.class);
            }
        };
        register(binder);


        // Ucitavamo resurse
        packages("com.example.raf_news_backend");
    }

}
package com.productreview.router;
import com.productreview.ProductReviewApplication;
import com.productreview.model.ProductReview;
import com.productreview.model.Review;
import com.productreview.repo.ProductReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes= ProductReviewApplication.class)
public class ProductReviewRouterTest
{
    @MockBean
    ReactiveCouchbaseRepository repo;
    @MockBean
    ProductReviewRepository repository;
    @Autowired
    private WebTestClient webClient;

    @Test
    public void testCreateProductReview() {
        List<Review> productReviewList = getProductReviews();
        ProductReview productReview = new ProductReview("M1234",productReviewList);

        given(repository.save(productReview)).willReturn(Mono.just(productReview));

        webClient.post()
                .uri("/review")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(productReview))
                .exchange()
                .expectStatus().isCreated();

        Mockito.verify(repository, times(1)).save(productReview);
    }

    @Test
    public void testGetProductReview() {
        List<Review> productReviewList = getProductReviews();
        ProductReview productReview = new ProductReview("M1234",productReviewList);

        given(repository.findById("M1234")).willReturn(Mono.just(productReview));

        webClient.get()
                .uri("/review/M1234")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ProductReview.class)
                .isEqualTo(productReview);

        Mockito.verify(repository, times(1)).findById("M1234");
    }

    @Test
    public void testUpdateProductReview() {
        List<Review> productReviewList = getProductReviews();
        ProductReview productReview = new ProductReview("M1234",productReviewList);
        ProductReview updatedProductReview = new ProductReview("M1234",productReviewList);
        updatedProductReview.getReviews().get(0).setRating(2.0);
        updatedProductReview.getReviews().get(0).setReviewComment("Pathetic Service");
        given(repository.save(productReview)).willReturn(Mono.just(updatedProductReview));
        given(repository.existsById("M1234")).willReturn(Mono.just(true));

        webClient.put()
                .uri("/review/M1234")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(productReview))
                .exchange()
                .expectStatus().isOk();
        Mockito.verify(repository, times(1)).existsById("M1234");
        Mockito.verify(repository, times(1)).save(productReview);
    }

    @Test
    public void testDeleteProductReview() {
        List<Review> productReviewList = getProductReviews();
        ProductReview productReview = new ProductReview("M1234",productReviewList);
        given(repository.findById("M1234")).willReturn(Mono.just(productReview));

        webClient.get()
                .uri("/review/M1234")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ProductReview.class)
                .isEqualTo(productReview);

        Mockito.verify(repository, times(1)).findById("M1234");
    }
    private List<Review> getProductReviews() {
        List<Review> productReviewList = new ArrayList<Review>();
        Review review = new Review("Review-123", "Awesome Product", 4);
        productReviewList.add(review);
        return productReviewList;
    }
}
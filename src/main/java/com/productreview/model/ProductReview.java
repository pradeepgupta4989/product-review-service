package com.productreview.model;

import com.couchbase.client.java.repository.annotation.Id;
import lombok.*;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.List;


@Document
@Data
@EqualsAndHashCode
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ProductReview {
	@Id
	private String productID;
	private List<Review> reviews;

}

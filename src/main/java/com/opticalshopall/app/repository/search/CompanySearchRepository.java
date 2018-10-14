package com.opticalshopall.app.repository.search;

import com.opticalshopall.app.domain.Company;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Company entity.
 */
public interface CompanySearchRepository extends ElasticsearchRepository<Company, Long> {
}

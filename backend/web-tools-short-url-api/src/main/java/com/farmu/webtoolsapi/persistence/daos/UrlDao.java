package com.farmu.webtoolsapi.persistence.daos;

import com.farmu.webtoolsapi.commons.result.Result;
import com.farmu.webtoolsapi.domain.UrlDto;

public interface UrlDao {
    Result<UrlDto> find(String hashUrl);
    Result<Boolean> save(UrlDto url);
    Result<Boolean> delete(UrlDto url);
}

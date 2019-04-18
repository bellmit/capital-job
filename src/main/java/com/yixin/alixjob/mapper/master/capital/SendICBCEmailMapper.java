package com.yixin.alixjob.mapper.master.capital;

import com.yixin.alixjob.dto.capital.ICBCEmailInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SendICBCEmailMapper {

    List<ICBCEmailInfoDto> queryICBCEmailInfoList();

    List<String> queryICBCEmailAddress(String key);

    void updateSendEmailStatusByAsqbh(@Param("asqbh") String asqbh,@Param("status") String status);
}

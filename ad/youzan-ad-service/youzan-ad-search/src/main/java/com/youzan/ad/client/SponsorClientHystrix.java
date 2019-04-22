package com.youzan.ad.client;

import com.youzan.ad.client.vo.AdPlanGetRequest;
import com.youzan.ad.client.vo.AdPlanResponse;
import com.youzan.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SponsorClientHystrix implements SponsorClient{

    @Override
    public CommonResponse<List<AdPlanResponse>> getPlan(AdPlanGetRequest request) {
        return new CommonResponse(-1,"系统异常",null);
    }

    


}

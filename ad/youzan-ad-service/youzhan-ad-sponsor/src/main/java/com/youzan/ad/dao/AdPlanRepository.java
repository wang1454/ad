package com.youzan.ad.dao;

import com.youzan.ad.entity.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdPlanRepository extends JpaRepository<AdPlan,Long>{
    /**
     * 根据广告主查询广告计划
     */
    List<AdPlan> findAllByUserId(Long userId);

    /**
     * 广告主查看广告计划
     */
    AdPlan findByIdAndUserId(Long id,Long userId);

    /**
     * 根据id和广告主id查询
     */
    List<AdPlan> findAllByIdAndUserId(List<Long> ids,Long userId);

    /**
     * 根据广告计划查询
     */
    List<AdPlan> findAllByPlanStatus(Integer planStatus);

    AdPlan findByUserIdAndPlanName(Long userId,String planName);
}
package com.springmvc.service.impl;

import com.springmvc.dao.FlightFareSourceDao;
import com.springmvc.pojo.FlightFare;
import com.springmvc.service.IFlightFareSourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class FlightFareSourceServiceImpl implements IFlightFareSourceService{

    @Resource
    private FlightFareSourceDao flightFareSourceDao;

    public int addFlightFareSource(FlightFare flightFare) {
        if (!validateData(flightFare, false)){
            System.out.println("添加的FlightFare中，部分参数有误，无法添加！");
            return 0;
        }
        flightFare.setLinkKey(UUID.randomUUID().toString().replace("-", "").toLowerCase());
        return flightFareSourceDao.insert(flightFare);
    }

    public int updateFlightFareSource(FlightFare flightFare) {
        if (!validateData(flightFare, true)){
            System.out.println("修改的FlightFare中，部分参数有误，无法修改！");
            return 0;
        }
        return flightFareSourceDao.updateByLinkKey(flightFare);
    }

    public FlightFare querySourceByLinkKey(String linkKey) {
        if (linkKey == null){
            System.out.println("linkKey不能为空！");
            return null;
        }
        return flightFareSourceDao.selectByLinkKey(linkKey);
    }

    private boolean validateData(FlightFare flightFare, boolean isUpdate){
        if (!isUpdate){
            if (flightFare.getDep() == null || flightFare.getDep().length() != 3) return false;
            if (flightFare.getArr() == null || flightFare.getArr().length() != 3) return false;
            if (flightFare.getDepTime() == null || flightFare.getDepTime().length() != 16) return false;
            if (flightFare.getFare() == null) return false;
            if (flightFare.getValidate() == null || (flightFare.getValidate() != 'Y' && flightFare.getValidate() != 'N')) return false;
        }else{
            if (flightFare.getLinkKey() == null) return false;
            if (flightFare.getDep() != null && flightFare.getDep().length() != 3) return false;
            if (flightFare.getArr() != null && flightFare.getArr().length() != 3) return false;
            if (flightFare.getDepTime() != null && flightFare.getDepTime().length() != 16) return false;
            if (flightFare.getValidate() != null && (flightFare.getValidate() != 'Y' && flightFare.getValidate() != 'N')) return false;
        }
        return true;
    }


}

package com.launchpad.vectordbservice.service;

import com.launchpad.vectordbservice.model.Spectacles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpectacleService  {

    @Tool (description = "Calculate the price of a spectacle")
    private Integer specPrice(Spectacles spectacles){
        log.info("Inside specPrice method");
        log.info("spectacle value received "+spectacles.toString());
        int price =0;
        if(spectacles.getLeftEyePower()>2 && spectacles.getRightEyePower()>2){
            price = price+200;
        }
        else{
            price=price+150;
        }
        if(spectacles.getBifocal()){
            price=price+75;
        }
        return price;
    }
}

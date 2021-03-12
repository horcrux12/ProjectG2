package com.test.helpdesk.controller;

import com.test.helpdesk.model.Teknisi;
import com.test.helpdesk.service.TeknisiService;
import com.test.helpdesk.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teknisi")
public class TeknisiController {
    @Autowired
    TeknisiService teknisiService;

    @GetMapping("")
    public ResponseEntity<?> getData (@RequestParam Map<Object,Object> params){
        if (params.isEmpty()){
            List<Teknisi> listTeknisi;
            try{
                listTeknisi = teknisiService.readData();
                return new ResponseEntity<>(listTeknisi, HttpStatus.OK);
            } catch (DataAccessException e) {
                System.out.println(e);
                return new ResponseEntity<>(new CustomErrorType("gagal mengambil data"), HttpStatus.BAD_GATEWAY);
            }
        }else {
            Teknisi teknisi = null;
            if (params.containsKey("idTeknisi") && !String.valueOf(params.get("idTeknisi")).isBlank()){
                teknisi = teknisiService.findDataById(String.valueOf(params.get("idTeknisi")));
            }
            if (params.containsKey("idUser") && !String.valueOf(params.get("idUser")).isBlank()){
                System.out.println(params.get("idUser"));
                teknisi = teknisiService.findDataByIdUser(String.valueOf(params.get("idUser")));
            }
            if (teknisi != null){
                return new ResponseEntity<>(teknisi, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new CustomErrorType("Gagal mengambil data"), HttpStatus.BAD_GATEWAY);
            }

        }
    }
}

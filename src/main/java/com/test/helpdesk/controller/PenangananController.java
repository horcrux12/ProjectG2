package com.test.helpdesk.controller;

import com.test.helpdesk.model.Penanganan;
import com.test.helpdesk.model.Tiket;
import com.test.helpdesk.service.PenangananService;
import com.test.helpdesk.service.TiketService;
import com.test.helpdesk.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api/penanganan")
public class PenangananController {
    @Autowired
    PenangananService penangananService;
    @Autowired
    TiketService tiketService;

    @GetMapping("")
    public ResponseEntity<?> getData(@RequestParam Map<Object, Object> params){
        List<Penanganan> listPenanganan;
        Map<String, Object> output= new HashMap<>();
        if (params.isEmpty()){
            try{
                listPenanganan = penangananService.readData();
                output.put("jumlah", penangananService.countAllData());
                output.put("data", listPenanganan);
                return new ResponseEntity<>(output, HttpStatus.OK);
            } catch (Exception e) {
                System.out.println(e);
                return new ResponseEntity<>(new CustomErrorType("Gagal mengambil data"), HttpStatus.BAD_GATEWAY);
            }
        }else{
            try{
                listPenanganan = penangananService.readDataByQuery(params);
                output.put("jumlah", penangananService.countAllDataQuery(params));
                output.put("data", listPenanganan);
                return new ResponseEntity<>(output, HttpStatus.OK);
            } catch (DataAccessException e) {
                System.out.println(e);
                return new ResponseEntity<>(new CustomErrorType("Gagal melakukan fecthing data"), HttpStatus.BAD_GATEWAY);
            }
        }
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/create")
    public ResponseEntity<?> createData(@RequestBody Penanganan penanganan){
        if (penanganan.getTeknisi().getIdTeknisi().isBlank() ||
                penanganan.getDataTiket().isEmpty()){
            return new ResponseEntity<>(new CustomErrorType("Data tidak valid"), HttpStatus.BAD_REQUEST);
        }else {
            Penanganan findPenanganan = penangananService.findByIdTeknisi(penanganan.getTeknisi().getIdTeknisi());
            if (findPenanganan != null){
                penanganan.setIdPenanganan(findPenanganan.getIdPenanganan());
                for (Tiket tiket : penanganan.getDataTiket()){
                    Tiket findTiket = tiketService.findById(tiket.getIdTiket());
                    if (findTiket == null){
                        return new ResponseEntity<>(new CustomErrorType("Tiket dengan id = '"+tiket.getIdTiket()+"' " +
                                "tidak tersedia"), HttpStatus.NOT_FOUND);
                    }
                }
                try {
                    penangananService.createData(penanganan);
                    return new ResponseEntity<>(new CustomErrorType("Berhasil melakukan penambahan data"), HttpStatus.CREATED);
                } catch (DataAccessException e) {
                    System.out.println(e);
                    return new ResponseEntity<>(new CustomErrorType("Gagal melakukan penambahan data"), HttpStatus.BAD_GATEWAY);
                }
            }else{
                return new ResponseEntity<>(new CustomErrorType("teknisi dengan id = '"+penanganan.getTeknisi().getIdTeknisi()+
                        "' tidak tersedia"), HttpStatus.BAD_REQUEST);
            }
        }
    }

    // Belum seleasi
    @GetMapping("/find-tiket")
    public ResponseEntity<?> findTiket(@RequestParam Map<Object, Object> params){
        Tiket tiket = penangananService.findDetailTiket(String.valueOf(params.get("idTiket")),
                String.valueOf(params.get("idPenanganan")));
        return new ResponseEntity<>(tiket, HttpStatus.OK);
    }


    // Belum selesai
}

package com.test.helpdesk.controller;

import com.test.helpdesk.model.Tiket;
import com.test.helpdesk.model.User;
import com.test.helpdesk.repository.TiketRepository;
import com.test.helpdesk.service.TiketService;
import com.test.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tiket")
public class TiketController {
    @Autowired
    TiketService tiketService;
    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getData(@RequestParam Map<Object, Object> params){
        List<Tiket> listTiket = new ArrayList<>();
        if (params.isEmpty()){
            try{
                listTiket = tiketService.readData();
                return new ResponseEntity<>(listTiket, HttpStatus.OK);
            } catch (DataAccessException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Gagal mengambil data", HttpStatus.BAD_GATEWAY);
            }
        }else{
            try{
                listTiket = tiketService.readDataByQuery(params);
                return new ResponseEntity<>(listTiket, HttpStatus.OK);
            } catch (DataAccessException e) {
                System.out.println(e);
                return new ResponseEntity<>("Gagal mengambil data", HttpStatus.BAD_GATEWAY);
            }
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createData(@RequestBody Tiket tiket){
        if (tiket.getProblemDesc().isBlank() || tiket.getUser().getIdUser().isBlank()){
            return new ResponseEntity<>("Data tidak valid", HttpStatus.BAD_REQUEST);
        }else{
            try{
                User findUser = userService.findById(tiket.getUser().getIdUser());
                if (findUser != null){
                    if (findUser.getRole().equals("User")){
                        tiket.setStatus("Pending");
                        tiket.setCreatedDate(new Date());
                        tiketService.createData(tiket);
                        return new ResponseEntity<>("Berhasil menambahkan data", HttpStatus.CREATED);
                    }else{
                        return new ResponseEntity<>("Tidak dapat membuat tiket", HttpStatus.UNAUTHORIZED);
                    }
                }else{
                    return new ResponseEntity<>("User dengan id = '"+tiket.getUser().getIdUser()+"' tidak tersedia", HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Gagal melakukan tambah data", HttpStatus.BAD_GATEWAY);
            }
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateData(@RequestBody Tiket tiket, @PathVariable("id") String id){
        try{
            Tiket findTiket = tiketService.findById(id);
            if (findTiket != null){
                if (tiket.getProblemDesc().isBlank()){
                    return new ResponseEntity<>("Data tidak valid", HttpStatus.BAD_REQUEST);
                }else{
                    findTiket.setProblemDesc(tiket.getProblemDesc());
                    findTiket.setUpdatedDate(new Date());
                    tiketService.updateData(findTiket);
                    return new ResponseEntity<>("Berhasil melakukan perubahan data", HttpStatus.OK);
                }
            }else {
                return new ResponseEntity<>("Data dengan id = '"+id+"' tidak tersedia", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Gagal melakukan perubahan data", HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("/change-status/{id}")
    public ResponseEntity<?> chengeStatus(@RequestBody Tiket tiket, @PathVariable("id") String id){
        try{
            Tiket findTiket = tiketService.findById(id);
            if (findTiket != null){
                if (tiket.getStatus().isBlank()){
                    return new ResponseEntity<>("Data tidak valid", HttpStatus.BAD_REQUEST);
                }else{
//                    if (tiket.getStatus().equals("Pending"))
                    findTiket.setStatus(tiket.getStatus());
                    findTiket.setUpdatedDate(new Date());
                    tiketService.updateData(findTiket);
                    return new ResponseEntity<>("Berhasil mengubah status", HttpStatus.OK);
                }
            }else {
                return new ResponseEntity<>("Data dengan id = '"+id+"' tidak tersedia", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Gagal melakukan perubahan status", HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteData(@PathVariable("id") String id){
        try{
            Tiket findTiket = tiketService.findById(id);
            if (findTiket != null){
                tiketService.deleteDataById(findTiket);
                return new ResponseEntity<>("Berhasil menghapus data", HttpStatus.OK);
            }else {
                return new ResponseEntity<>("Data dengan id = '"+id+"' tidak tersedia", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Gagal melakukan hapus data", HttpStatus.BAD_GATEWAY);
        }
    }
}

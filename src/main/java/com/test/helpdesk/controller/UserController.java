package com.test.helpdesk.controller;

import com.test.helpdesk.model.User;
import com.test.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;
    PasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("")
    public ResponseEntity<?> getData (@RequestParam Map<Object, Object> params){
        List<User> users = new ArrayList<>();
        if (params.isEmpty()){
            try{
                users = userService.readData();
                return new ResponseEntity<>(users, HttpStatus.OK);
            } catch (DataAccessException e) {
                e.printStackTrace();
                return new ResponseEntity<>(users, HttpStatus.BAD_GATEWAY);
            }
        }else {
            try{
                users = userService.readDataByQuery(params);
                return new ResponseEntity<>(users, HttpStatus.OK);
            } catch (DataAccessException e) {
                return new ResponseEntity<>("Gagal melakukan fecthing data", HttpStatus.BAD_GATEWAY);
            }
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createData(@RequestBody User user){
        if (user.getNama().isBlank() || user.getPassword().isBlank() ||
                user.getRole().isBlank() || user.getUsername().isBlank()){
            return new ResponseEntity<>("data tidak boleh kosong", HttpStatus.BAD_REQUEST);
        }else{
            Pattern p = Pattern.compile("[a-zA-Z0-9.\\\\-_]{3,}");
            Matcher m = p.matcher(user.getUsername());
            if (m.matches()){
                if(Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[?!@#$%^&+=]).{8,}$", user.getPassword())){
                    try {
                        User findUser = userService.findByUsername(user.getUsername());
                        if (findUser == null){
                            userService.createData(user);
                            return new ResponseEntity<>("Berhasil melakukan input data", HttpStatus.CREATED);
                        }else{
                            return new ResponseEntity<>("User dengan username = '"+user.getUsername()+"' telah tersedia", HttpStatus.CONFLICT);
                        }
                    } catch (DataAccessException e) {
                        e.printStackTrace();
                        return new ResponseEntity<>("Gagal melakukan input data", HttpStatus.BAD_GATEWAY);
                    }
                }else{
                    return new ResponseEntity<>("Password harus terdiri dari 8 character atau lebih," +
                            " dengan huruf besar dan kecil, angka dan memiliki special character", HttpStatus.BAD_REQUEST);
                }
            }else{
                return new ResponseEntity<>("Username harus terdiri dari minimal 3 huruf," +
                        " tidak boleh menggunakan spasi, dan tidak boleh memiliki spesial karakter", HttpStatus.BAD_REQUEST);
            }
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateData(@PathVariable("id") String id, @RequestBody User user){
        if (user.getNama().isBlank() || user.getPassword().isBlank() ||
                user.getRole().isBlank() || user.getUsername().isBlank()){
            return new ResponseEntity<>("data tidak sesuai", HttpStatus.BAD_REQUEST);
        }else{
            try {
                User findUser = userService.findById(id);
                if (findUser != null){
                    // regex username
                    if (Pattern.matches("[a-zA-Z0-9.\\\\-_]{3,}", user.getUsername())){
                        //regex password
                        if(Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[?!@#$%^&+=]).{8,}$", user.getPassword())){
                            // Update sessions
                            user.setIdUser(findUser.getIdUser());
                            userService.updateData(user);
                            return new ResponseEntity<>("Berhasil melakukan update data", HttpStatus.CREATED);
                            // End Update sessions
                        }else{
                            return new ResponseEntity<>("Password harus terdiri dari 8 character atau lebih," +
                                    " dengan huruf besar dan kecil, angka dan memiliki special character", HttpStatus.BAD_REQUEST);
                        }
                        // End regex password
                    }else{
                        return new ResponseEntity<>("Username harus terdiri dari minimal 3 huruf," +
                                " tidak boleh menggunakan spasi, dan tidak boleh memiliki spesial karakter", HttpStatus.BAD_REQUEST);
                    }
                    // End regex username
                }else{
                    return new ResponseEntity<>("User dengan username = '"+user.getUsername()+"' tidak tersedia", HttpStatus.NOT_FOUND);
                }
            } catch (DataAccessException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Gagal melakukan update data", HttpStatus.EXPECTATION_FAILED);
            }
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteData (@PathVariable("id") String id){
        try{
            User findUser = userService.findById(id);
            if (findUser == null){
                return new ResponseEntity<>("user dengan id ='"+id+"' tidak dapat ditemukan", HttpStatus.NOT_FOUND);
            }else {
                userService.deleteDataById(findUser);
                return new ResponseEntity<>("berhasil menghapus data", HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Gagal menghapus data", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/auth")
    public ResponseEntity<?> auth (@RequestParam Map<Object, Object> params){
        if (params.isEmpty()){
            return new ResponseEntity<>("Data post tidak sesuai", HttpStatus.BAD_REQUEST);
        }else{
            if (params.containsKey("username") && !String.valueOf(params.get("username")).isBlank()) {
                User user = userService.findByUsername(String.valueOf(params.get("username")));
                if (user != null){
                    if (params.containsKey("password") && !String.valueOf(params.get("password")).isBlank()){
                        if (encoder.matches((CharSequence) params.get("password"), user.getPassword())){
                            return new ResponseEntity<>(user, HttpStatus.OK);
                        }else{
                            return new ResponseEntity<>("Kombinasi user atau password salah", HttpStatus.NOT_FOUND);
                        }
                    }else{
                        return new ResponseEntity<>("masukkan paramater password", HttpStatus.BAD_REQUEST);
                    }
                }else {
                    return new ResponseEntity<>("Kombinasi user atau password salah", HttpStatus.NOT_FOUND);
                }
            }else{
                new ResponseEntity<>("masukkan parameter username", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("masukkan paramater username dan password", HttpStatus.BAD_REQUEST);
        }
    }
}

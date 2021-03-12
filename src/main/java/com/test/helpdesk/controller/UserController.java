package com.test.helpdesk.controller;

import com.test.helpdesk.Constants;
import com.test.helpdesk.model.User;
import com.test.helpdesk.service.UserService;
import com.test.helpdesk.util.CustomErrorType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;
    PasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("")
    public ResponseEntity<?> getData (@RequestParam Map<Object, Object> params){
        List<User> users = new ArrayList<>();
        Map<String, Object> output= new HashMap<>();
        if (params.isEmpty()){
            try{
                users = userService.readData();
                output.put("jumlah", userService.countAllData());
                output.put("data", users);
                return new ResponseEntity<>(output, HttpStatus.OK);
            } catch (DataAccessException e) {
                System.out.println(e);
                return new ResponseEntity<>(new CustomErrorType("Gagal melakukan fecthing data"), HttpStatus.BAD_GATEWAY);
            }
        }else {
            try{
                users = userService.readDataByQuery(params);
                output.put("jumlah", userService.countAllDataQuery(params));
                output.put("data", users);
                return new ResponseEntity<>(output, HttpStatus.OK);
            } catch (DataAccessException e) {
                System.out.println(e);
                return new ResponseEntity<>(new CustomErrorType("Gagal melakukan fecthing data"), HttpStatus.BAD_GATEWAY);
            }
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createData(@RequestBody User user){
        if (user.getNama().isBlank() || user.getPassword().isBlank() ||
                user.getRole().isBlank() || user.getUsername().isBlank()){
            return new ResponseEntity<>(new CustomErrorType("data tidak boleh kosong"), HttpStatus.BAD_REQUEST);
        }else{
            Pattern p = Pattern.compile("[a-zA-Z0-9.\\\\-_]{3,}");
            Matcher m = p.matcher(user.getUsername());
            if (m.matches()){
                if(Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[?!@#$%^&+=]).{8,}$", user.getPassword())){
                    try {
                        User findUser = userService.findByUsername(user.getUsername());
                        if (findUser == null){
                            userService.createData(user);
                            return new ResponseEntity<>(new CustomErrorType("Berhasil melakukan input data"), HttpStatus.CREATED);
                        }else{
                            return new ResponseEntity<>(new CustomErrorType("User dengan username = '"+user.getUsername()+"' telah tersedia"), HttpStatus.CONFLICT);
                        }
                    } catch (DataAccessException e) {
//                        e.printStackTrace();
                        System.out.println(e);
                        return new ResponseEntity<>(new CustomErrorType("Gagal melakukan input data"), HttpStatus.BAD_GATEWAY);
                    }
                }else{
                    return new ResponseEntity<>(new CustomErrorType("Password harus terdiri dari 8 character atau lebih," +
                            " dengan huruf besar dan kecil, angka dan memiliki special character"), HttpStatus.BAD_REQUEST);
                }
            }else{
                return new ResponseEntity<>(new CustomErrorType("Username harus terdiri dari minimal 3 huruf," +
                        " tidak boleh menggunakan spasi, dan tidak boleh memiliki spesial karakter"), HttpStatus.BAD_REQUEST);
            }
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateData(@PathVariable("id") String id, @RequestBody User user){
        if (user.getNama().isBlank() || user.getPassword().isBlank() ||
                user.getRole().isBlank() || user.getUsername().isBlank()){
            return new ResponseEntity<>(new CustomErrorType("data tidak sesuai"), HttpStatus.BAD_REQUEST);
        }else{
            try {
                User findUser = userService.findById(id);
                if (findUser != null){
                    // regex username
                    if (Pattern.matches("[a-zA-Z0-9.\\\\-_]{3,}", user.getUsername())){
                        //regex password
                        if(Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[?!@#$%^&+=]).{8,}$", user.getPassword())){
                            // Update sessions
                            User validate = userService.validateUser(user.getUsername(), findUser.getIdUser());
                            if (validate == null){
                                user.setIdUser(findUser.getIdUser());
                                userService.updateData(user);
                                return new ResponseEntity<>(new CustomErrorType("Berhasil melakukan update data"), HttpStatus.CREATED);
                            }else{
                                return new ResponseEntity<>(new CustomErrorType("User dengan username = '"+user.getUsername()+"' telah tersedia"), HttpStatus.CONFLICT);
                            }
                            // End Update sessions
                        }else{
                            return new ResponseEntity<>(new CustomErrorType("Password harus terdiri dari 8 character atau lebih," +
                                    " dengan huruf besar dan kecil, angka dan memiliki special character"), HttpStatus.BAD_REQUEST);
                        }
                        // End regex password
                    }else{
                        return new ResponseEntity<>(new CustomErrorType("Username harus terdiri dari minimal 3 huruf," +
                                " tidak boleh menggunakan spasi, dan tidak boleh memiliki spesial karakter"), HttpStatus.BAD_REQUEST);
                    }
                    // End regex username
                }else{
                    return new ResponseEntity<>(new CustomErrorType("User dengan id = '"+user.getIdUser()+"' tidak tersedia"), HttpStatus.NOT_FOUND);
                }
            } catch (DataAccessException e) {
                e.printStackTrace();
                return new ResponseEntity<>(new CustomErrorType("Gagal melakukan update data"), HttpStatus.EXPECTATION_FAILED);
            }
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteData (@PathVariable("id") String id){
        try{
            User findUser = userService.findById(id);
            if (findUser == null){
                return new ResponseEntity<>(new CustomErrorType("user dengan id ='"+id+"' tidak dapat ditemukan"), HttpStatus.NOT_FOUND);
            }else {
                userService.deleteDataById(findUser);
                return new ResponseEntity<>(new CustomErrorType("berhasil menghapus data"), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new CustomErrorType("Gagal menghapus data"), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth (@RequestBody User user){
        if (user.getUsername().isBlank() || user.getPassword().isBlank()){
            return new ResponseEntity<>(new CustomErrorType("Data post tidak sesuai"), HttpStatus.BAD_REQUEST);
        }else{
            if (!user.getUsername().isBlank()) {
                User findUser = userService.findByUsername(user.getUsername());
                if (findUser != null){
                    if (!user.getPassword().isBlank()){
                        if (encoder.matches((CharSequence) user.getPassword(), findUser.getPassword())){
                            return new ResponseEntity<>(findUser, HttpStatus.OK);
                        }else{
                            return new ResponseEntity<>(new CustomErrorType("Kombinasi user atau password salah"), HttpStatus.NOT_FOUND);
                        }
                    }else{
                        return new ResponseEntity<>(new CustomErrorType("masukkan paramater password"), HttpStatus.BAD_REQUEST);
                    }
                }else {
                    return new ResponseEntity<>(new CustomErrorType("Kombinasi user atau password salah"), HttpStatus.NOT_FOUND);
                }
            }else{
                new ResponseEntity<>(new CustomErrorType("masukkan parameter username"), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(new CustomErrorType("masukkan paramater username dan password"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/authtoken")
    public ResponseEntity<?> authToken (@RequestBody User user){
        if (user.getUsername().isBlank() || user.getPassword().isBlank()){
            return new ResponseEntity<>(new CustomErrorType("Data post tidak sesuai"), HttpStatus.BAD_REQUEST);
        }else{
            if (!user.getUsername().isBlank()) {
                User findUser = userService.findByUsername(user.getUsername());
                if (findUser != null){
                    if (!user.getPassword().isBlank()){
                        if (encoder.matches((CharSequence) user.getPassword(), findUser.getPassword())){
                            return new ResponseEntity<>(generatedJWTToken(findUser), HttpStatus.OK);
                        }else{
                            return new ResponseEntity<>(new CustomErrorType("Kombinasi user atau password salah"), HttpStatus.NOT_FOUND);
                        }
                    }else{
                        return new ResponseEntity<>(new CustomErrorType("masukkan paramater password"), HttpStatus.BAD_REQUEST);
                    }
                }else {
                    return new ResponseEntity<>(new CustomErrorType("Kombinasi user atau password salah"), HttpStatus.NOT_FOUND);
                }
            }else{
                new ResponseEntity<>(new CustomErrorType("masukkan parameter username"), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(new CustomErrorType("masukkan paramater username dan password"), HttpStatus.BAD_REQUEST);
        }
    }

    private Map<String, String> generatedJWTToken(User user){
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("idUser", user.getIdUser())
                .claim("username", user.getUsername())
                .claim("nama", user.getNama())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token",token);
        return map;
    }
}

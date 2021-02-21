import React, { Component } from 'react'

import {Input,Textarea, Label, Select, FormField} from "../../../components/form";
import Button from "../../../components/button";
import "./style.css";

class FormTeknisi extends Component {
    constructor(props) {
        super(props);
        this.state = {  
            username: {
                value : "",
                validation : "none"
            },
            password: {
                value : "",
                validation : "none"
            },
            role: {
                value : "Teknisi",
                validation : "none"
            },
            nama: {
                value : "",
                validation : "none"
            }
        }
    }

    componentDidMount (){
        if (this.props.id) {
            fetch("http://localhost:8080/api/users?idUser="+this.props.id, {
            method : "GET",
            headers: {"Content-type": "application/json; charset=UTF-8"}
        })
        .then(resp => {
            if (!resp.ok) {
                return resp.json().then(text => {
                    throw new Error(`${text.messages}`);
                })
            }
            return resp.json();
        })
        .then((json) => {
            console.log(json);
            this.setState({
                username : {...this.state.username, value : json.data[0].username},
                nama : {...this.state.nama, value : json.data[0].nama},
                role : {...this.state.role, value : json.data[0].role},
            })
        })
        .catch((status) => {
            alert(status)
        })
        }
    }

    getValue = (el) => {
        let status = "none";
        const pattUname = new RegExp("[a-zA-Z0-9.\\-_]{3,}");
        const pattPass = new RegExp("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[?!@#$%^&+=]).{8,}$");

        if (el.target.name == "username"){
            if (!pattUname.test(el.target.value)){
                status = "block";
            }
        }else if (el.target.name == "password") {
            if (!pattPass.test(el.target.value)){
                status = "block";
            }
        }else{
            if (el.target.value.trim().length < 1) {
                status = "block";
            }
        }

        this.setState({
            [el.target.name] : {validation : status, value : el.target.value}
        })
    }

    onSubmitHandler = () => {
        const pattUname = new RegExp("[a-zA-Z0-9.\\-_]{3,}");
        const pattPass = new RegExp("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[?!@#$%^&+=]).{8,}$")
        const { username, password, nama, role} = this.state;
        let dataLogin = {
            username: username.value,
            password: password.value,
            role: role.value,
            nama: nama.value
        }

        if (pattUname.test(username.value) && 
            pattPass.test(password.value) &&
            nama.value.trim().length > 0
        ) {
            if (this.props.id) {
                fetch("http://localhost:8080/api/users/update/"+this.props.id, {
                    method : "PUT",
                    body : JSON.stringify(dataLogin),
                    headers: {"Content-type": "application/json; charset=UTF-8"}
                })
                .then(resp => {
                    if (!resp.ok) {
                        return resp.json().then(text => {
                            throw new Error(`${text.messages}`);
                        })
                    }
                    return resp.json();
                })
                .then((json) => {
                    alert(json.messages);
                    this.clearForm();
                    this.props.history.push("/table-teknisi");
                })
                .catch((status) => {
                    alert(status)
                })
            }else{
                fetch("http://localhost:8080/api/users/create", {
                    method : "POST",
                    body : JSON.stringify(dataLogin),
                    headers: {"Content-type": "application/json; charset=UTF-8"}
                })
                .then(resp => {
                    if (!resp.ok) {
                        return resp.json().then(text => {
                            throw new Error(`${text.messages}`);
                        })
                    }
                    return resp.json();
                })
                .then((json) => {
                    alert(json.messages);
                    this.clearForm();
                    this.props.history.push("/table-user");
                })
                .catch((status) => {
                    alert(status)
                })
            }
        }else{
            alert("Harap isi kolom form dengan benar!!");
        }
    }

    clearForm = () => {
        this.setState({
            username: {
                value : "",
                validation : "none"
            },
            password: {
                value : "",
                validation : "none"
            },
            role: {
                value : "",
                validation : "none"
            },
            nama: {
                value : "",
                validation : "none"
            }
        })
    }

    render() { 
        const { username, password, nama, role} = this.state;
        return (  
            <div className="form-user">
                <div className="judul">
                    Form Input Teknisi
                </div>
                <div className="form">
                    <FormField classes="field-form">
                        <Label classes="label-form">Nama</Label>
                        <Input inputProp={{
                            inputType : "text",
                            inputname : "nama", 
                            classes : "form-input",
                            inputPh : "Masukkan nama",
                            inputVal : nama.value,
                            funcSet : this.getValue
                        }}/>
                    </FormField>
                    <small style={{
                            color : "red",
                            display : nama.validation,
                        }}> Field tidak boleh kosong !!</small>
                    <FormField classes="field-form">
                        <Label classes="label-form">Username</Label>
                        <Input inputProp={{
                            inputType : "text",
                            inputname : "username", 
                            classes : "form-input",
                            inputPh : "Masukkan username",
                            inputVal : username.value,
                            funcSet : this.getValue
                        }}/>
                    </FormField>
                    <small style={{
                            color : "red",
                            display : username.validation,
                        }}> * username harus terdiri dari minimal 3 karakter </small>
                    <FormField classes="field-form">
                        <Label classes="label-form">Password</Label>
                        <Input inputProp={{
                            inputType : "password",
                            inputname : "password", 
                            classes : "form-input",
                            inputPh : "Masukkan password",
                            inputVal : password.value,
                            funcSet : this.getValue
                        }}/>
                        <small style={{
                            color : "red",
                            display : password.validation
                        }}> * password harus teridiri dari minimal 8 karakter dengan huruf besar dan kecil, nomor dan special karakter</small>
                    </FormField>
                    {/* <FormField classes="field-form">
                        <Label classes="label-form">Role</Label>
                        <Select
                            selectName="role"
                            funcSet={this.getValue}
                            classes="form-input"
                            valSelected={role.value}
                            optionElement={[
                            {
                                optionVal: "",
                                textOption: "Pilih Role",
                            },
                            {
                                optionVal: "Admin",
                                textOption: "Admin",
                            },
                            {
                                optionVal: "Teknisi",
                                textOption: "Teknisi",
                            },
                            {
                                optionVal: "User",
                                textOption: "User",
                            }]}
                        />
                    <small style={{
                            color : "red",
                            display : role.validation,
                        }}> Field tidak boleh kosong !!</small>
                    </FormField> */}
                    <button className="btn btn-hijau" onClick={this.onSubmitHandler}>Submit</button>
                    <Button class="btn btn-kuning" funcClick={() => {this.props.history.push("/table-teknisi")}}>Cencel</Button>
                </div>
            </div>
        );
    }
}
 
export default FormTeknisi;
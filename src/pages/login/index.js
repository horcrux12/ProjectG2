import React, { Component } from 'react';
import { connect } from "react-redux";

import Button from '../../components/button';
import {Input,Textarea, Label, Select, FormField} from "../../components/form"
import "./style.css";
import Action from "../../action"

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {  
            username : "",
            password : "",
        }
    }

    // Function
    getValue = (el) => {
        this.setState({
            [el.target.name] : el.target.value
        });
    }

    handleSubmit = (e)=>{
        fetch("http://localhost:8080/api/users/auth", {
            method : "POST",
            body : JSON.stringify(this.state),
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
        .then(json => {
            alert("Login berhasil");
            let dataLogin = {
                idUser: json.idUser,
                username: json.username,
                role: json.role,
                nama: json.nama
            }
            this.props.doLogin(dataLogin);
        })
        .catch(e => {
            alert("Kombinasi Username dan Password salah !!")
        });
        // alert("Login!!");
    }

    // END Function

    render() { 
        const {username, password} = this.state;

        if(this.props.auth.statusLogin){
            this.props.history.push("/");
        }
        // console.log(this.props);

        return (  
            <div className="login-page">
                <div className="container">
                    <div className="header">
                        <div className="text-judul">
                            <h1>LOGIN</h1>
                        </div>
                    </div>
                    
                    <div className="content">
                        <div className="form">
                            <FormField classes="field-form">
                                <Label classes="label-form">Username</Label>
                                <Input inputProp={{
                                    inputType : "text",
                                    inputname : "username", 
                                    classes : "form-input",
                                    inputPh : "Masukkan Username",
                                    inputVal : username,
                                    funcSet : this.getValue
                                }}/>
                            </FormField>
                            <FormField classes="field-form">
                                <Label classes="label-form">Password</Label>
                                <Input inputProp={{
                                    inputType : "password",
                                    inputname : "password", 
                                    classes : "form-input",
                                    inputPh : "Masukkan Password",
                                    inputVal : password,
                                    funcSet : this.getValue
                                }}/>
                            </FormField>
                            <FormField classes="field-submit">
                                <Button class="btn-submit" funcClick={this.handleSubmit}>LOGIN</Button>
                            </FormField>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

const mapStateToProps = state => {
    return{
        auth : state.AuthReducer
    }
}

const mapDispatchToProps = dispatch => {
    return{
        doLogin : (dataLogin) => dispatch({ type : Action.LOGIN_SUCCCESS, payload : dataLogin})
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Login);
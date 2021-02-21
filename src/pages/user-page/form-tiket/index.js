import React, { Component } from 'react';
import { connect } from "react-redux";

import {Input,Textarea, Label, Select, FormField} from "../../../components/form";
import Button from "../../../components/button";
import "./style.css";

class FormTiketUser extends Component {
    constructor(props) {
        super(props);
        this.state = {  
            problemDesc: {
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
        
        if (el.target.value.trim().length < 1) {
            status = "block";
        }

        this.setState({
            [el.target.name] : {validation : status, value : el.target.value}
        })
    }

    onSubmitHandler = () => {
        const { problemDesc} = this.state;
        let dataInput = {
            problemDesc : problemDesc.value,
            user : {
                idUser : this.props.auth.dataLogin.idUser
            }
        }

        if (problemDesc.value.trim().length > 0) {
            if (this.props.id) {
                fetch("http://localhost:8080/api/users/update/"+this.props.id, {
                    method : "PUT",
                    body : JSON.stringify(dataInput),
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
            }else{
                fetch("http://localhost:8080/api/tiket/create", {
                    method : "POST",
                    body : JSON.stringify(dataInput),
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
                    this.props.history.push("/table-tiket");
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
            problemDesc: {
                value : "",
                validation : "none"
            }
        })
    }

    render() { 
        const { problemDesc} = this.state;
        return (  
            <div className="form-user">
                <div className="judul">
                    Form Helpdesk
                </div>
                <div className="form">
                    <FormField classes="field-form">
                        <Label classes="label-form">Problem</Label>
                        <Textarea
                            inputRows = "5"
                            funcSet= {this.getValue}
                            class = "form-input"
                            inputname = "problemDesc"
                            value={problemDesc.value}
                            inputPc = "Masukkan Probelem"
                        />
                    </FormField>
                    <small style={{
                            color : "red",
                            display : problemDesc.validation,
                        }}> Field tidak boleh kosong !!</small>
                    <button className="btn btn-hijau" onClick={this.onSubmitHandler}>Submit</button>
                    <Button class="btn btn-kuning" funcClick={() => {this.props.history.push("/table-user")}}>Cencel</Button>
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

export default connect(mapStateToProps)(FormTiketUser);
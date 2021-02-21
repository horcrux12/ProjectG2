import React, { Component } from 'react'

import {Input,Textarea, Label, Select, FormField} from "../../../components/form";
import Button from "../../../components/button";
import "./style.css";

class FormPenugasan extends Component {
    constructor(props) {
        super(props);
        this.state = { 
            dataTeknisi : [],
            dataTiket : {}, 
            idTeknisi: {
                value : "",
                validation : "none"
            }
        }
    }

    componentDidMount (){
        fetch("http://localhost:8080/api/teknisi",{
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
                dataTeknisi : json
            }, () => {
                fetch("http://localhost:8080/api/tiket?idTiket="+this.props.id, {
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
                        dataTiket : json.data[0]
                    })
                })
                .catch((status) => {
                    alert(status)
                });
            })
        })
        .catch((status) => {
            alert(status)
        });
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
        const { idTeknisi, dataTiket } = this.state;
        let dataInput = {
            teknisi: {
                idTeknisi: idTeknisi.value
            },
            dataTiket: [
                {
                    idTiket: dataTiket.idTiket
                }
            ]
        }

        if (idTeknisi.value != "") {
            fetch("http://localhost:8080/api/penanganan/create", {
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
        }else{
            alert("Harap isi semua kolom form dengan benar!!");
        }
    }

    clearForm = () => {
        this.setState({
            dataTeknisi : [],
            dataTiket : {}, 
            idTeknisi: {
                value : "",
                validation : "none"
            }
        })
    }

    render() { 
        const { idTeknisi, dataTeknisi, dataTiket } = this.state;
        let dataSelect = [];
        console.log();
        dataSelect = dataTeknisi.map(el => {
            return {
                optionVal: el.idTeknisi,
                textOption: el.user.nama
            }
        });

        return (  
            <div className="form-user">
                <div className="judul">
                    Form Penugasan Teknisi
                </div>
                <div className="form">
                    <FormField classes="field-form">
                        <Label classes="label-form">Problem : </Label>
                        <div className="form-input" style={{textAlign: "left", border:"none"}}>{dataTiket.problemDesc}</div>
                    </FormField>

                    <FormField classes="field-form">
                        <Label classes="label-form">Nama Pemohon : </Label>
                        <div className="form-input" style={{textAlign: "left", border:"none"}}>{(dataTiket.user) ? dataTiket.user.nama : ""}</div>
                    </FormField>
                    
                    <FormField classes="field-form">
                        <Label classes="label-form">Pilih Teknisi</Label>
                        <Select
                            selectName="idTeknisi"
                            funcSet={this.getValue}
                            classes="form-input"
                            valSelected={idTeknisi.value}
                            optionElement={[
                                {
                                    optionVal: "",
                                    textOption: "Pilih Role",
                                },
                                ...dataSelect
                            ]}
                        />
                    <small style={{
                            color : "red",
                            display : idTeknisi.validation,
                        }}> Field tidak boleh kosong !!</small>
                    </FormField>
                    <button className="btn btn-hijau" onClick={this.onSubmitHandler}>Submit</button>
                    <Button class="btn btn-kuning" funcClick={() => {this.props.history.push("/table-tiket")}}>Cencel</Button>
                </div>
            </div>
        );
    }
}
 
export default FormPenugasan;
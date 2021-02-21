import React, { Component } from 'react'

import "./style.css";
import {Input,Textarea, Label, Select, FormField} from "../../../components/form";
import Button from '../../../components/button';
import Tr from "../../../components/table_row";
import { Link } from 'react-router-dom';

class TableTeknisi extends Component {
    constructor(props) {
        super(props);
        this.state = {  
            search : "",
            limit : 5,
            offset : 0,
            currPage : 1,
            jumlah : 0,
            page : 0,
            dataUser : [],
            startRow : 1,
            maxRow : 5
        }
    }

    // ------------FUNCTION---------------
    componentDidMount(){
        this.getData();
    }

    getValue = (el) => {
        this.setState({
            [el.target.name] : el.target.value
        })
    }

    deleteFunc = (id)=>{
        if(window.confirm("apakah Anda yakin ingin menghapus data ini ?")){
            fetch("http://localhost:8080/api/users/delete/"+id, {
                method : "DELETE"
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
                alert(json.messages);
                this.getData();
            })
            .catch(e => {
                alert("Gagal mengambil data")
            });
        }
    }

    editFunc = (id)=>{
        console.log(id);
        this.props.history.push("/form-teknisi/"+id)
    }

    tambahButton = () => {
        this.props.history.push("/form-teknisi");
    }

    searchFunc = (el) => {
        const {limit , offset} = this.state;
        this.setState({
            search : el.target.value,
            currPage : 1
        }, () => {
            this.getData();
        })
        // this.getData(`http://localhost:8080/api/users?search=${el.target.value}&limit=${limit}&offset=${offset}`)
    }

    filterLimit = (el) =>{
        this.setState({
            [el.target.name] : parseInt(el.target.value),
            currPage : 1
        }, () => {
            const {limit , offset} = this.state;
            this.getData();
        });

        
    }

    getData = () => {
        const {search, limit, offset, currPage, maxRow, page, startRow} = this.state
        let url = `http://localhost:8080/api/users?role=Teknisi&limit=${limit}&offset=${offset}`;
        if (search != "") {
            url += `&search=${search}`;
        }
        fetch(`${url}`, {
            method : "GET"
        })
        .then(resp => { return resp.json()})
        .then(json => {
            let start = 1;
            let deff = Math.floor(maxRow/2);
            if((currPage - deff) <= 1){
                start = 1;
            }else{
                let temp = currPage - deff
                if((temp + (maxRow - 1)) > page){
                    start = page - (maxRow - 1);
                }else{
                    start = temp;
                }
            }
            this.setState({
                dataUser : json.data,
                jumlah : json.jumlah,
                page : Math.ceil(json.jumlah/this.state.limit),
                startRow : start
            }, () => {
                console.log(this.state);
            });
        })
        .catch(e => {
            console.log(e);
            alert("Gagal mengambil data")
        });
    }

    setCurrPage = (currentClick) => {
        this.setState({
            currPage : currentClick,
            offset : (currentClick*this.state.limit) - this.state.limit
        }, () => {
            this.getData();
        })
    }

    // ------------END FUNCTION---------------

    render() { 
        const {search, dataUser, limit, startRow, maxRow, page} = this.state;
        let dataTable;
        let pages = [];
        if(dataUser.length < 1){
            dataTable = (
                <tr key={1}>
                    <td colSpan="5">Data tidak tersedia</td>
                </tr>
            )
        }else{
            dataTable = dataUser.map((el, indx) => {
                return(
                    <Tr key={indx} id={el.idUser} delFunc={this.deleteFunc} editFunc={this.editFunc}>
                        <td>{el.idUser}</td>
                        <td>{el.nama}</td>
                        <td>{el.username}</td>
                        <td>{el.role}</td>
                    </Tr>
                )
            })
        }

        for (let i = startRow; i <= maxRow; i++) {
            if (i <= page) {
                pages.push(
                    <Button key={i} class={i == this.state.currPage ? "active" : "pageClick"} funcClick={() => {this.setCurrPage(i)}}>{i}</Button>
                );
            }
            
        } 
        return (  
            <div className="table-user">
                <div className="judul">
                    Tabel Teknisi
                </div>
                <div className="header-table">
                    <div className="search-section">
                        <Label classes="label-search">Search </Label>
                        <Input inputProp={{
                            inputType : "text",
                            inputname : "search", 
                            classes : "field-form",
                            inputPh : "cari data",
                            funcSet : this.searchFunc
                        }}/>
                    </div>
                    <div className="button-tambah">
                            <Button class="btn btn-hijau tambah" funcClick={this.tambahButton}>Tambah data Teknisi</Button>
                        {/* <Link to="/tambah">
                        </Link> */}
                    </div>
                </div>
                <div className="search-section">
                    <Label classes="label-search">limit </Label>
                    <Select
                        selectName="limit"
                        funcSet={this.filterLimit}
                        classes="field-form"
                        valSelected={limit}
                        optionElement={[
                            {
                                optionVal: "5",
                                textOption: "5",
                            },
                            {
                                optionVal: "10",
                                textOption: "10",
                            },
                            {
                                optionVal: "15",
                                textOption: "15",
                            }
                        ]}
                    />
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nama</th>
                            <th>Username</th>
                            <th>Role</th>
                            <th>Aksi</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            dataTable
                        }
                    </tbody>
                </table>
                <div className="pagination">
                    {
                        pages
                    }
                </div>
            </div>
        );
    }
}
 
export default TableTeknisi;
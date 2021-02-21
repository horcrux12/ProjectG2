import React, { Component } from 'react';

class Tr extends Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }

    render() { 
        const data = this.props;
        let dataColumn = ""
        if(data.type == "deletedOnly"){
            return ( 
                <tr>
                    {this.props.children}
                    <td>
                        <button onClick={() => {data.delFunc(data.id)}}>hapus</button>
                    </td>
                </tr>
            );
        }else if(data.type == "assignTiket"){
            return(
                <tr>
                    {this.props.children}
                    <td>
                        <button className="btn-small btn-kuning" onClick={() => {data.editFunc(data.id)}}>Tugaskan</button>
                        <button className="btn-small btn-merah" onClick={() => {data.delFunc(data.id)}}>hapus</button>
                    </td>
                </tr>
            )
        }
        return ( 
            <tr>
                {this.props.children}
                <td>
                    <button className="btn-small btn-kuning" onClick={() => {data.editFunc(data.id)}}>edit</button>
                    <button className="btn-small btn-merah" onClick={() => {data.delFunc(data.id)}}>hapus</button>
                </td>
            </tr>
        );
        
    }
}
 
export default Tr;
import React, { Component } from 'react';
import { connect } from "react-redux";

class Homepage extends Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }
    render() { 
        return (  
            <div className="home-page">
                <h1>Selamat datang di website Helpdesk {this.props.dataLogin.nama} !!</h1>
            </div>
        );
    }
}

const mapStateToProps = state => {
    return{
        dataLogin : state.AuthReducer.dataLogin
    }
}

export default connect(mapStateToProps)(Homepage);
import React, { Component } from 'react';
import { connect } from "react-redux";
import { Link } from "react-router-dom"

class SideBar extends Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }
    render() { 
        const {auth, properties} = this.props;

        let sidebarItem = [];

        if (auth.dataLogin.role == "Admin") {
            sidebarItem = [
                {
                    name : "Home",
                    path : "/",
                },
                {
                    name : "User",
                    path : "/table-user",
                },
                {
                    name : "Teknisi",
                    path : "/table-teknisi",
                },
                {
                    name : "Tiket",
                    path : "/table-tiket",
                }
            ]
        }
        
        return (  
            <div className="sidebar">
                <div className="icon-web">
                    Helpdesk
                </div>
                {
                    sidebarItem.map( (el, indx) => {
                        return(
                            <Link key={indx} to={el.path} className={properties.history.location.pathname == el.path ? "active" : ""}>{el.name}</Link>
                        )
                    })
                }
            </div>
        );
    }
}

const mapStateToProps = state => {
    return{
        auth : state.AuthReducer
    }
  }
 
export default connect(mapStateToProps)(SideBar);
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

        if (auth.dataLogin.role == "User") {
            sidebarItem = [
                {
                    name : "Home",
                    path : "/",
                },
                {
                    name : "Tiket",
                    path : "/table-tiket",
                },
                {
                    name : "Profile",
                    path : "/profile/"+this.props.auth.dataLogin.idUser
                }
            ]
        }

        if (auth.dataLogin.role == "Teknisi") {
            sidebarItem = [
                {
                    name : "Home",
                    path : "/",
                },
                {
                    name : "Profile",
                    path : "/profile/"+this.props.auth.dataLogin.idUser
                },
                {
                    name : "Tiket",
                    path : "/tiket-teknisi",
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
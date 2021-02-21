import React, { Component } from 'react'
import { Body, Menu, SideBar } from "../component";
import "./style.css";

class Main extends Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }
    render() {
        
        if(!this.props.statusLogin){
            this.props.properties.history.push("/login");
        }
        return (  
            <div className="main">
                <SideBar properties={this.props.properties}/>
                <div className="content">
                    <Menu/>
                    <Body properties={this.props.properties}/>
                </div>
            </div>
        );
    }
}

export default Main;
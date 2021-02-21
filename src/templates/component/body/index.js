import React, { Component } from 'react';
import { Route, Switch } from 'react-router-dom';
import { Homepage, TableUser, FormUser } from "../../../pages"

class Body extends Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }
    render() {
        if (!this.props.statusLogin) {
            this.props.properties.history.push("/login")
        }
        return (  
            <div className="main-content">
                    {this.props.comp}
            </div>
        );
    }
}

export default Body;
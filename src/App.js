import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch, useHistory, useParams } from "react-router-dom";
import { connect } from "react-redux";

import { FormTeknisi, FormUser, Homepage,
   Login, TableTeknisi, TableUser, TabelTiket,
   FormPenugasan } from "./pages";
import { Main } from "./templates";
import Action from "./action";
import { Body, Menu, SideBar } from "./templates/component";
import "./App.css";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {  }
  }

  template = (properties, component) => {
    return(
      <div className="main">
          <SideBar properties={properties}/>
          <div className="content">
              <Menu/>
              <Body statusLogin={this.props.auth.statusLogin} properties={properties} comp={component}/>
          </div>
      </div>
    )
  }

  render() { 
    
    return (  
      <>
        <Router>
          <Switch>
            <Route path="/" exact component={
              (props) => {
                let history = useHistory();
                return(
                  this.template(props, <Homepage/>)
                    // <Main properties={props} statusLogin={this.props.auth.statusLogin}/>
                )
              }
            }/>
            {/* User sessions */}
            <Route path="/table-user" exact component={
              (props) => {
                let history = useHistory();
                return(
                  this.template(props, <TableUser history={history}/>)
                )
              }
            }/>
            <Route path="/form-user/" exact component={
              (props) => {
                let history = useHistory();
                return(
                  this.template(props, <FormUser history={history}/>)
                )
              }
            }/>
            <Route path="/form-user/:id" exact component={
              (props) => {
                let history = useHistory();
                const { id } = useParams()
                return(
                  this.template(props, <FormUser id={id} history={history}/>)
                )
              }
            }/>
            {/* User Sessions */}
            {/* Teknisi Sessions */}
            <Route path="/table-teknisi" exact component={
              (props) => {
                let history = useHistory();
                return(
                  this.template(props, <TableTeknisi history={history}/>)
                )
              }
            }/>
            <Route path="/form-teknisi/" exact component={
              (props) => {
                let history = useHistory();
                return(
                  this.template(props, <FormTeknisi history={history}/>)
                )
              }
            }/>
            <Route path="/form-teknisi/:id" exact component={
              (props) => {
                let history = useHistory();
                const { id } = useParams();
                return(
                  this.template(props, <FormTeknisi id={id} history={history}/>)
                )
              }
            }/>
            {/* Teknisi Sessions */}
            {/* Tiket Sessions */}

            <Route path="/table-tiket" exact component={
              (props) => {
                let history = useHistory();
                return(
                  this.template(props, <TabelTiket history={history}/>)
                )
              }
            }/>

            <Route path="/form-penugasan/:id" exact component={
                (props) => {
                  let history = useHistory();
                  const { id } = useParams();
                  return(
                    this.template(props, <FormPenugasan history={history} id={id}/>)
                  )
                }
              }/>

            {/* Tiket Sessions */}

            <Route path="/login" exact component={
              () => {
                let history = useHistory();
                return(<Login history={history}/>)
              }
            }/>
          </Switch>
        </Router>
      </>
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

export default connect(mapStateToProps, mapDispatchToProps)(App);
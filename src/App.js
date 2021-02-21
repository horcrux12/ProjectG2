import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch, useHistory, useParams } from "react-router-dom";
import { connect } from "react-redux";

import { FormTeknisi, FormUser, Homepage,
   Login, TableTeknisi, TableUser, TabelTiket,
   FormPenugasan, Page404, Profil } from "./pages";
import { TiketUser, FormTiketUser } from "./pages/user-page";
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
              <Menu properties={properties}/>
              <Body statusLogin={this.props.auth.statusLogin} properties={properties} comp={component}/>
          </div>
      </div>
    )
  }

  pages = () => {
    if (this.props.auth.dataLogin.role == "Admin") {
      return (
        <div>
          
        </div>  
      )
    }
  }


  render() { 

    let pages = [];
    if(this.props.auth.dataLogin.role == "Admin"){
      pages.push(<Route path="/table-user" exact component={
        (props) => {
          let history = useHistory();
          return(
            this.template(props, <TableUser history={history}/>)
          )
        }
      }/>)
      pages.push(<Route path="/form-user/" exact component={
        (props) => {
          let history = useHistory();
          return(
            this.template(props, <FormUser history={history}/>)
          )
        }
      }/>)
      pages.push(<Route path="/form-user/:id" exact component={
        (props) => {
          let history = useHistory();
          const { id } = useParams();
          return(
            this.template(props, <FormUser id={id} history={history}/>)
          )
        }
      }/>)
      pages.push(<Route path="/table-teknisi" exact component={
        (props) => {
          let history = useHistory();
          return(
            this.template(props, <TableTeknisi history={history}/>)
          )
        }
      }/>)
      pages.push(<Route path="/form-teknisi/" exact component={
        (props) => {
          let history = useHistory();
          return(
            this.template(props, <FormTeknisi history={history}/>)
          )
        }
      }/>)
      pages.push(<Route path="/form-teknisi/:id" exact component={
        (props) => {
          let history = useHistory();
          const { id } = useParams();
          return(
            this.template(props, <FormTeknisi id={id} history={history}/>)
          )
        }
      }/>)

      pages.push(<Route path="/table-tiket" exact component={
        (props) => {
          let history = useHistory();
          return(
            this.template(props, <TabelTiket history={history}/>)
          )
        }
      }/>)

      pages.push(<Route path="/form-penugasan/:id" exact component={
          (props) => {
            let history = useHistory();
            const { id } = useParams();
            return(
              this.template(props, <FormPenugasan history={history} id={id}/>)
            )
          }
        }/>)
    }

    if(this.props.auth.dataLogin.role == "User"){
      pages.push(<Route path="/table-tiket" exact component={
        (props) => {
          let history = useHistory();
          return(
            this.template(props, <TiketUser history={history}/>)
          )
        }
      }/>)
      pages.push(<Route path="/tambah-tiket" exact component={
        (props)=>{
          let history = useHistory();
          return(
            this.template(props, <FormTiketUser history={history}/>)
          )
        }
      }/>)
      pages.push(<Route path="/edit-tiket/:id" exact component={
        (props)=>{
          const { id } = useParams();
          let history = useHistory();
          return(
            this.template(props, <FormTiketUser id={id} history={history}/>)
          )
        }
      }/>)
      pages.push(<Route path="/profile/:id" exact component={
        (props)=>{
          const { id } = useParams();
          let history = useHistory();
          return(
            this.template(props, <Profil id={id} dataLogin={this.props.auth.dataLogin} history={history}/>)
          )
        }
      }/>)
    }
    
    if(this.props.auth.dataLogin.role == "Teknisi"){
      pages.push(<Route path="/profile/:id" exact component={
        (props)=>{
          const { id } = useParams();
          let history = useHistory();
          return(
            this.template(props, <Profil id={id} dataLogin={this.props.auth.dataLogin} history={history}/>)
          )
        }
      }/>)
    }
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

            {pages}
            
            <Route path="/login" exact component={
              () => {
                let history = useHistory();
                return(<Login history={history}/>)
              }
            }/>

            <Route component={Page404}/>
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
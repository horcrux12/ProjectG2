const loginDefault = {
    statusLogin : false,
    dataLogin : {
        idUser: "",
        username: "",
        role: "",
        nama: ""
    }
};

//Reducer
const authReducer = (state = loginDefault, action) => {
   switch (action.type) {
       case 'LOGIN_SUCCCESS':
           console.log(action.payload);
           return{
               ...state,
               statusLogin : true,
               dataLogin : action.payload
           }
       case 'LOGOUT' :
           return loginDefault
       default:
           return state;
   }
}

export default authReducer;
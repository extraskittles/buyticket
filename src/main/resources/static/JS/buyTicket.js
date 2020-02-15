//从url获取参数
let getQuery = function (variable) {
    let query = window.location.search.substring(1);
    let vars = query.split("&");
    for (let i = 0; i < vars.length; i++) {
        let pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }
    return (false);
}


//获取用户信息
let getUser=function(){
    axios.get("http://zhouzhaorong.xyz/user/").then((response) => {
        let code = response.data.code;
        if (code == 200) {
            let user = response.data.data;
            this.user = user;
        }
    }).catch(error => {
            /*alert("error")*/
        }
    )
}




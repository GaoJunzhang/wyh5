const http = require('http');
const fs = require('fs');
const urls = require('url');
const querystring = require('querystring');
var userList = [];
const servers = http.createServer(function (req,res) {
    "use strict";
    var info = urls.parse(req.url,true);
    if(info.pathname =='/api'){
        switch (info.query.action){
            case 'zc':
                let isZc = true;
                userList.forEach((item,index)=>{
                    if(item.userName == info.query.userName){
                        isZc = false;
                    }
                });
                if(isZc){
                    userList.push(info.query);
                    res.write('{ok:1,msg:"注册成功"}')
                }else {
                    res.write('{ok:1,msg:"该用户已存在"}')
                }
                break;
            case 'login':
                let userInfo={};
                userList.forEach((item,index)=>{
                    if(item.userName == info.query.userName){
                        userInfo = item;
                    }
                });
                if(userInfo.userName){
                    res.write(`{ok:1,msg:"登录成功欢迎您${userInfo.userName}"}`)
                }else {
                    res.write('{ok:0,msg:"登录失败，查无'+info.query.userName+'此人"}')
                }
                break;
        }
        res.end();
    }else {
        fs.readFile(`./${info.pathname}`,{},function (err,data) {
            if(err){
                res.write('404');
            }else {
                res.write(data);
            }
            res.end();
        })
    }
})
servers.listen('8019');
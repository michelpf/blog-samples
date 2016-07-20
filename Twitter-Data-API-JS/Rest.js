var mysql = require("mysql");
function REST_ROUTER(router,connection,md5) {
    var self = this;
    self.handleRoutes(router,connection,md5);
}

REST_ROUTER.prototype.handleRoutes= function(router,connection,md5) {
    router.get("/",function(req,res){
              res.json({"Message" : "As operações disponíveis para GET são: topFollowers, hashCount e tweetHour."});
    });

    
   router.get("/topFollowers",function(req,res){
        var query = "select id, name, followers from ?? order by followers desc limit 5";
        var table = "user";
        query = mysql.format(query,table);
        connection.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "Success", "Users" : rows});
            }
        });
    });

   router.get("/hashCount",function(req,res){
        var query = "select hashtag, count(1) from ?? where lang='pt' group by hashtag;";
        var table = "status";
        query = mysql.format(query,table);
        connection.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "Success", "Status" : rows});
            }
        });
    });

      router.get("/tweetHour",function(req,res){
        var query = "select count(1), hour(created) from ??  group by hour(created) order by hour(created);";
        var table = "status";
        query = mysql.format(query,table);
        connection.query(query,function(err,rows){
            if(err) {
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                res.json({"Error" : false, "Message" : "Success", "Status" : rows});
            }
        });
    });
    

}

module.exports = REST_ROUTER;
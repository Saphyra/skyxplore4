function Optional(obj){
    const value = obj;

    this.isPresent = function(){
        return value !== null && value !== undefined;
    }

    this.orElseGet = function(func){
        return this.isPresent() ? value : func();
    }
}
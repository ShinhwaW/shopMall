app.controller('searchController',function($scope,searchService){
	
	//搜索
	$scope.search=function(){
		searchService.search($scope.searchMap).success(
			function(response){
				$scope.resultMap=response;				
			}
		);		
	}

	//搜索对象
	$scope.searchMap={'keywords':'','category':'','brand':'','spec':{}};

	$scope.addSearchItem=function (key,value) {
		if (key=='category' || key=='brand'){
			$scope.searchMap[key]=value;
		} else {
			$scope.searchMap.spec[key]=value;
		}
		$scope.search();
    };

    $scope.removeSearchItem=function (key) {
        if (key=='category' || key=='brand'){
            $scope.searchMap[key]="";
        } else {
            delete $scope.searchMap.spec[key];
        }
        $scope.search();
    };



});
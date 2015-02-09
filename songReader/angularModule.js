/**
 * Created by tatianaBeliaieva on 2/9/15.
 */
var app = angular.module("mainModule", []);

app.controller("controllerBlock", function ($scope) {
    $scope.hidePlayerBlock = false;
    $scope.hideLibraryBlock = true;

    $scope.showPlayerBlock = function () {
        $scope.hidePlayerBlock = false;
        $scope.hideLibraryBlock = true;
    };

    $scope.showLibraryBlock = function () {
        $scope.hidePlayerBlock = true;
        $scope.hideLibraryBlock = false;
    };
});

app.controller("testController", function ($scope) {
    $scope.canEditSong = true;

    $scope.canEditFile = function () {
        $scope.canEditSong = !$scope.canEditSong;
    };

});

var fileList;
function getInfo() {
    songInfoApplet.popUpFileChooser();
    fileList = JSON.parse(document.songInfoApplet.getFilePath());
}

app.controller('appletController', ['$scope', function ($scope) {
    document.getElementById("getInfoButton").addEventListener('click', function () {
        $scope.$apply(function () {
            getInfo();
            $scope.fileListArray = fileList;
        });
    });

    $scope.tableElementsArray = [{columnName: 'Song', columnValue: '', columnType: 'TIT2'}, {
        columnName: 'Album',
        columnValue: '',
        columnType: 'TALB'
    },
        {columnName: 'Band', columnValue: '', columnType: 'TPE2'}, {
            columnName: 'Year',
            columnValue: '',
            columnType: 'TYER'
        }, {columnName: 'Genre', columnValue: '', columnType: 'TCON'}];

    $scope.callSongInfo = function (name) {
        var songInfo = JSON.parse(document.songInfoApplet.getSongInfo(name));
      //   alert("Info " + songInfo.TIT2);
        $scope.tableElementsArray = [{
            columnName: 'Song',
            columnValue: songInfo.TIT2,
            columnType: 'TIT2'
        }, {columnName: 'Album', columnValue: songInfo.TALB, columnType: 'TALB'},
            {columnName: 'Band', columnValue: songInfo.TPE2, columnType: 'TPE2'}, {
                columnName: 'Year',
                columnValue: songInfo.TYER,
                columnType: 'TYER'
            }, {columnName: 'Genre', columnValue: songInfo.TCON, columnType: 'TCON'}];
    };

    $scope.saveSongInfomation = function () {
        songInfoApplet.setSongInformation(JSON.stringify($scope.tableElementsArray));
    };
}]);

/*var fileList;
 function getInfo() {
 console.log(fileList);
 songInfoApplet.popUpFileChooser();
 console.log("2");
 fileList = JSON.parse(document.songInfoApplet.getFilePath());
 console.log("3");
 }*/


/*document.getElementById("songsList") = function(e) {
 window.scrollTo(document.body.scrollLeft,
 document.body.scrollTop + 500);
 };*/

angular.module('seiso', [ 'ngRoute', 'ngSanitize', 'ui.bootstrap', 'seisoFilters', 'seisoServices', 'seisoControllers' ])
	.config([ '$httpProvider', '$routeProvider', 'paginationConfig', function($httpProvider, $routeProvider, paginationConfig) {		
		$httpProvider.defaults.headers.common = {
			// TODO Migrate toward application/hal+json
			"Accept" : "application/json",
			// https://spring.io/blog/2015/01/12/the-login-page-angular-js-and-spring-security-part-ii 
			"X-Requested-With" : "XMLHttpRequest"
//			"X-User-Agent" : "Seiso UI"
		};
		
		var route = function(controllerName, viewName) {
			return {
				controller: controllerName + 'Controller',
				templateUrl: 'view/' + viewName + '.html'
			};
		}
		
		var viewRoute = function(shortViewName) {
			return {
				templateUrl: "view/items/" + shortViewName + ".html"
			}
		}
		
		$routeProvider
				.when('/', route('Home', 'home/home'))
				.when('/search', route('Search', 'search/search'))
				.when('/login', route('Login', 'login/login'))
				.when('/admin', route('Admin', 'admin/index'))
				.when('/mb', route('MB', 'mb/index'))
				.when('/mb/:type', route('MBProfile', 'mb/profile'))
				.when('/data-centers', viewRoute("data-center/list/data-center-list"))
				.when('/data-centers/:key', route('DataCenterDetails', 'items/data-center/details/data-center-details'))
				.when('/environments', viewRoute("environment/list/environment-list"))
				.when('/environments/:key', route('EnvironmentDetails', 'items/environment/details/environment-details')) 
				.when('/load-balancers', route('LoadBalancerList', 'items/load-balancer/list/load-balancer-list'))
				.when('/load-balancers/:name', route('LoadBalancerDetails', 'items/load-balancer/details/load-balancer-details'))
				.when('/machines/:name', route('MachineDetails', 'items/machine/details/machine-details'))
				.when('/nodes/:name', route('NodeDetails', 'items/node/details/node-details'))
				.when('/people', route('PersonList', 'items/person/list/person-list'))
				.when('/people/:username', route('PersonDetails', 'items/person/details/person-details'))
				.when('/services', route('ServiceList', 'items/service/list/service-list'))
				.when('/services/:key', viewRoute("service/details/service-details"))
				.when('/service-instances', route('ServiceInstanceList', 'items/service-instance/list/service-instance-list'))
				.when('/service-instances/:key', viewRoute("service-instance/details/service-instance-details"))
				.when('/statuses', route('StatusList', 'items/status/list/status-list'))
				.when('/types', route('TypeList', 'items/type/list/type-list'))
				.otherwise({ redirectTo : '/' });
				
		// Pagination configuration. Is this the right place to do this?
		paginationConfig.itemsPerPage = 50;
		paginationConfig.maxSize = 7;
		paginationConfig.boundaryLinks = true;
		// FIXME Want to use &laquo;, &lsaquo;, etc., but Angular is escaping the &. [WLW] 
		paginationConfig.firstText = '<<';
		paginationConfig.previousText = "<";
		paginationConfig.nextText = '>';
		paginationConfig.lastText = '>>';
	} ])
	
	// See https://docs.angularjs.org/guide/di
	// paginationConfig is an existing constant. We're updating it here.
	.run([ '$rootScope', function($rootScope) {
		$rootScope.model = {
			page: {
				title: 'Seiso'
			}
		};
		$rootScope.uri = function(repoKey, itemKey) {
			if (repoKey == null) {
				return '#/';
			} else if (itemKey == null) {
				return '#/' + repoKey;
			} else {
				return '#/' + repoKey + '/' + itemKey;
			}
		}
	} ]);
